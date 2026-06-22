import BookCard from "./BookCard";

function BookGrid({
  books,
  likedBooks,
  tbrBooks
}) {
  return (
    <div className="book-grid">

      {books.map((book) => (
        <BookCard
  key={book.id}
  book={book}
  likedBooks={likedBooks}
  tbrBooks={tbrBooks}
/>
      ))}

    </div>
  );
}

export default BookGrid;