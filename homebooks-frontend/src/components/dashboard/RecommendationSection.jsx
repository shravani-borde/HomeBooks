import BookCard from "../books/BookCard";

function RecommendationSection({
  books,
  likedBooks,
  tbrBooks
}) {

  return (
    <section className="dashboard-section">

      <h2>
        Recommended For You
      </h2>

      <div className="book-grid">

        {books.length > 0 ? (

          books.map(book => (

            <BookCard
              key={book.id}
              book={book}
              likedBooks={likedBooks}
              tbrBooks={tbrBooks}
            />

          ))

        ) : (

          <p>
            Like a few books to
            get personalized
            recommendations.
          </p>

        )}

      </div>

    </section>
  );
}

export default RecommendationSection;