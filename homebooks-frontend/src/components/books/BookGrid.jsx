import BookCard from "./BookCard";

function BookGrid({ books }) {
  return (
    <div className="book-grid">

      {books.map((book) => (
        <BookCard
          key={book.id}
          book={book}
        />
      ))}

    </div>
  );
}

export default BookGrid;