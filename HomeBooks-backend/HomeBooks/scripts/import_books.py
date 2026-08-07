import requests
import random
import time
import mysql.connector

# ----------------------------
# Database Connection
# ----------------------------
db = mysql.connector.connect(
    host="localhost",
    user="root",
    password="root",          # Change if your MySQL password is different
    database="HomeBooks"
)

cursor = db.cursor(buffered=True)

# ----------------------------
# Genres to Import
# ----------------------------
genres = [
    "fantasy",
    "romance",
    "mystery",
    "thriller",
    "science_fiction",
    "history",
    "self_help",
    "horror",
    "biography",
    "classics"
]

# ----------------------------
# Import Books
# ----------------------------
for genre in genres:

    print(f"\n========== IMPORTING {genre.upper()} ==========")

    imported = 0

    # 5 pages × 50 books = 250 books max per genre
    for page in range(5):

        offset = page * 50

        url = (
            f"https://openlibrary.org/search.json"
            f"?subject={genre}"
            f"&limit=50"
            f"&offset={offset}"
        )

        books = []

        # Retry 3 times if OpenLibrary times out
        for attempt in range(3):

            try:

                response = requests.get(
                    url,
                    timeout=20
                )

                response.raise_for_status()

                books = response.json().get("docs", [])

                break

            except Exception as e:

                print(
                    f"Retry {attempt + 1}/3 "
                    f"for {genre} page {page + 1}"
                )

                time.sleep(2)

        if not books:
            print(f"Skipping page {page + 1}")
            continue

        for book in books:

            title = book.get("title")

            if not title:
                continue

            author = book.get(
                "author_name",
                ["Unknown"]
            )[0]

            cover = book.get("cover_i")

            if cover:
                coverImage = (
                    f"https://covers.openlibrary.org/b/id/{cover}-L.jpg"
                )
            else:
                coverImage = None

            description = (
                "Description not available."
            )

            rating = 0.0

            # Skip duplicate books
            cursor.execute(
                """
                SELECT COUNT(*)
                FROM books
                WHERE title=%s
                AND author=%s
                """,
                (title, author)
            )

            if cursor.fetchone()[0] > 0:
                continue

            cursor.execute(
                """
                INSERT INTO books
                (
                    title,
                    author,
                    genre,
                    description,
                    rating,
                    cover_image
                )
                VALUES
                (%s,%s,%s,%s,%s,%s)
                """,
                (
                    title,
                    author,
                    genre,
                    description,
                    rating,
                    coverImage
                )
            )

            imported += 1

        db.commit()

    print(f"Imported {imported} new books.")

# ----------------------------
# Close Connection
# ----------------------------
cursor.close()
db.close()

print("\n✅ Finished importing all books!")