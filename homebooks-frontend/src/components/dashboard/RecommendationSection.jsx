import BookCard from "../books/BookCard";

function RecommendationSection({
  books
}) {
  return (
    <section id="recommendations">

      <h2>
        Recommendations For You ⭐
      </h2>

      <div className="recommendation-grid">
        {books.map((book) => (
          <BookCard
            key={book.id}
            book={book}
          />
        ))}
      </div>

    </section>
  );
}

export default RecommendationSection;