import sys
import joblib
import pandas as pd

from sklearn.metrics.pairwise import cosine_similarity

# ----------------------------
# Load dataset & model
# ----------------------------
books = pd.read_csv("ml/dataset.csv")

vectorizer = joblib.load("ml/model.pkl")

# ----------------------------
# Create features
# ----------------------------
books["features"] = (
    books["title"].fillna("") + " " +
    books["author"].fillna("") + " " +
    books["genre"].fillna("") + " " +
    books["description"].fillna("")
)

# ----------------------------
# Transform books
# ----------------------------
tfidf_matrix = vectorizer.transform(
    books["features"]
)

# ----------------------------
# Read book id from command line
# ----------------------------
book_id = int(sys.argv[1])

index = books.index[
    books["id"] == book_id
].tolist()[0]

# ----------------------------
# Similarity
# ----------------------------
similarity = cosine_similarity(
    tfidf_matrix[index],
    tfidf_matrix
)

scores = list(
    enumerate(similarity[0])
)

scores = sorted(
    scores,
    key=lambda x: x[1],
    reverse=True
)

# Skip itself
scores = scores[1:11]

# Print recommended IDs
for i, score in scores:

    print(
        books.iloc[i]["id"]
    )