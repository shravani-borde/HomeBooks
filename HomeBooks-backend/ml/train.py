import mysql.connector
import pandas as pd
import joblib

from sklearn.feature_extraction.text import TfidfVectorizer

# -----------------------------
# Connect to MySQL
# -----------------------------
db = mysql.connector.connect(
    host="localhost",
    user="root",
    password="root",
    database="HomeBooks"
)

query = """
SELECT
    id,
    title,
    author,
    genre,
    description,
    rating
FROM books
"""

books = pd.read_sql(query, db)

db.close()

print("Books loaded:", len(books))

# -----------------------------
# Create one text column
# -----------------------------
books["features"] = (
    books["title"].fillna("") + " " +
    books["author"].fillna("") + " " +
    books["genre"].fillna("") + " " +
    books["description"].fillna("")
)

# -----------------------------
# TF-IDF
# -----------------------------
vectorizer = TfidfVectorizer(
    stop_words="english"
)

tfidf_matrix = vectorizer.fit_transform(
    books["features"]
)

print("TF-IDF Matrix Shape:")
print(tfidf_matrix.shape)

# -----------------------------
# Save Model
# -----------------------------
joblib.dump(vectorizer, "ml/model.pkl")

books.to_csv(
    "ml/dataset.csv",
    index=False
)

print("\nModel Saved!")
print("Dataset Saved!")