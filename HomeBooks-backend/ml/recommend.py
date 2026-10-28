import sys
import mysql.connector
import pandas as pd
import joblib

from sklearn.metrics.pairwise import cosine_similarity


# ============================================================
# 1. Get user email
# ============================================================

if len(sys.argv) < 2:
    print("ERROR: User email required")
    sys.exit(1)

email = sys.argv[1]


# ============================================================
# 2. Connect to MySQL
# ============================================================

db = mysql.connector.connect(
    host="localhost",
    user="root",
    password="root",
    database="HomeBooks"
)


# ============================================================
# 3. Load books
# ============================================================

books_query = """
SELECT
    id,
    title,
    author,
    genre,
    description,
    rating
FROM books
"""

books = pd.read_sql(
    books_query,
    db
)


# ============================================================
# 4. Find user
# ============================================================

user_query = """
SELECT id
FROM users
WHERE email = %s
"""

user_df = pd.read_sql(
    user_query,
    db,
    params=(email,)
)

if user_df.empty:

    print("ERROR: User not found")

    db.close()

    sys.exit(1)


user_id = int(
    user_df.iloc[0]["id"]
)


# ============================================================
# 5. Get liked books
# ============================================================

liked_query = """
SELECT book_id
FROM user_liked_books
WHERE user_id = %s
"""

liked_df = pd.read_sql(
    liked_query,
    db,
    params=(user_id,)
)

liked_ids = set(
    liked_df["book_id"].astype(int)
    if not liked_df.empty
    else []
)


# ============================================================
# 6. Get TBR books
# ============================================================

tbr_query = """
SELECT book_id
FROM user_tbr_books
WHERE user_id = %s
"""

tbr_df = pd.read_sql(
    tbr_query,
    db,
    params=(user_id,)
)

tbr_ids = set(
    tbr_df["book_id"].astype(int)
    if not tbr_df.empty
    else []
)


# ============================================================
# 7. Get ratings
# ============================================================

ratings_query = """
SELECT
    book_id,
    score
FROM ratings
WHERE user_id = %s
"""

ratings_df = pd.read_sql(
    ratings_query,
    db,
    params=(user_id,)
)


# ============================================================
# 8. Get favorite genres
# ============================================================

genres_query = """
SELECT genre
FROM user_favorite_genres
WHERE user_id = %s
"""

genres_df = pd.read_sql(
    genres_query,
    db,
    params=(user_id,)
)

favorite_genres = set(
    genres_df["genre"].astype(str).str.lower()
    if not genres_df.empty
    else []
)


db.close()


# ============================================================
# 9. Create book features
# ============================================================

books["features"] = (
    books["title"].fillna("") + " " +
    books["author"].fillna("") + " " +
    books["genre"].fillna("") + " " +
    books["description"].fillna("")
)


# ============================================================
# 10. Load TF-IDF model
# ============================================================

vectorizer = joblib.load(
    "ml/model.pkl"
)

tfidf_matrix = vectorizer.transform(
    books["features"]
)


# ============================================================
# 11. Build user profile
# ============================================================

positive_vectors = []
positive_weights = []

negative_vectors = []
negative_weights = []

# ------------------------------------------------------------
# Liked books
# ------------------------------------------------------------

for book_id in liked_ids:

    matches = books.index[
        books["id"] == book_id
    ].tolist()

    if matches:

        positive_vectors.append(
            tfidf_matrix[matches[0]]
        )

        positive_weights.append(3.0)


# ------------------------------------------------------------
# TBR books
# ------------------------------------------------------------

for book_id in tbr_ids:

    matches = books.index[
        books["id"] == book_id
    ].tolist()

    if matches:

        positive_vectors.append(
            tfidf_matrix[matches[0]]
        )

        positive_weights.append(1.5)


# ------------------------------------------------------------
# Rated books
# ------------------------------------------------------------

for _, row in ratings_df.iterrows():

    book_id = int(row["book_id"])
    score = float(row["score"])

    matches = books.index[
        books["id"] == book_id
    ].tolist()

    if not matches:
        continue

    vector = tfidf_matrix[matches[0]]

    # -----------------------------------------
    # High rating = positive preference
    # -----------------------------------------

    if score >= 8:

        weight = (score - 7) / 3.0

        positive_vectors.append(vector)
        positive_weights.append(weight)

    # -----------------------------------------
    # Medium rating = neutral
    # -----------------------------------------

    elif score >= 5:

        # Don't strongly influence the profile
        continue

    # -----------------------------------------
    # Low rating = negative preference
    # -----------------------------------------

    else:

        weight = (5 - score) / 4.0

        negative_vectors.append(vector)
        negative_weights.append(weight)

# ============================================================
# 12. Handle users with no interactions
# ============================================================

if not positive_vectors and not negative_vectors:

    if favorite_genres:

        genre_mask = books["genre"].fillna(
            ""
        ).str.lower().isin(
            favorite_genres
        )

        recommendations = books[
            genre_mask
        ]

        recommendations = recommendations[
            ~recommendations["id"].isin(
                liked_ids | tbr_ids
            )
        ]

        recommendations = recommendations.sort_values(
            "rating",
            ascending=False
        )

        for book_id in recommendations["id"]:
            print(int(book_id))

    else:

        recommendations = books.sort_values(
            "rating",
            ascending=False
        )

        for book_id in recommendations["id"]:
            print(int(book_id))

    sys.exit(0)

# ============================================================
# 13. Create positive user profile
# ============================================================

positive_profile = None

if positive_vectors:

    positive_profile = (
        positive_vectors[0]
        * positive_weights[0]
    )

    for vector, weight in zip(
        positive_vectors[1:],
        positive_weights[1:]
    ):

        positive_profile += (
            vector * weight
        )


# ============================================================
# 14. Create negative user profile
# ============================================================

negative_profile = None

if negative_vectors:

    negative_profile = (
        negative_vectors[0]
        * negative_weights[0]
    )

    for vector, weight in zip(
        negative_vectors[1:],
        negative_weights[1:]
    ):

        negative_profile += (
            vector * weight
        )

# ============================================================
# 15. Calculate positive similarity
# ============================================================

if positive_profile is not None:

    positive_similarity = cosine_similarity(
        positive_profile,
        tfidf_matrix
    ).flatten()

else:

    positive_similarity = (
        pd.Series(
            0.0,
            index=range(len(books))
        ).values
    )


# ============================================================
# 16. Calculate negative similarity
# ============================================================

if negative_profile is not None:

    negative_similarity = cosine_similarity(
        negative_profile,
        tfidf_matrix
    ).flatten()

else:

    negative_similarity = (
        pd.Series(
            0.0,
            index=range(len(books))
        ).values
    )


books["positive_similarity"] = (
    positive_similarity
)

books["negative_similarity"] = (
    negative_similarity
)


# ============================================================
# 17. Remove already interacted books
# ============================================================

excluded_ids = (
    liked_ids |
    tbr_ids |
    set(
        ratings_df["book_id"].astype(int)
        if not ratings_df.empty
        else []
    )
)

books = books[
    ~books["id"].isin(excluded_ids)
]


# ============================================================
# 18. Give favorite genres a small boost
# ============================================================

if favorite_genres:

    books["genre_boost"] = books[
        "genre"
    ].fillna("").str.lower().apply(
        lambda genre:
        0.15 if genre in favorite_genres
        else 0.0
    )

else:

    books["genre_boost"] = 0.0


books["final_score"] = (
    books["positive_similarity"]
    - (
        1.1 *
        books["negative_similarity"]
    )
    + books["genre_boost"]
)

print("Liked books:", len(liked_ids))
print("TBR books:", len(tbr_ids))
print("Rated books:", len(ratings_df))
print("Positive vectors:", len(positive_vectors))
print("Negative vectors:", len(negative_vectors))
print("Favorite genres:", favorite_genres)
print("Books available for recommendation:", len(books))

# ============================================================
# 19. Top 10 recommendations
# ============================================================

recommendations = books.sort_values(
    "final_score",
    ascending=False
)


# ============================================================
# 20. Return only book IDs
# ============================================================

for book_id in recommendations["id"]:

    print(int(book_id))