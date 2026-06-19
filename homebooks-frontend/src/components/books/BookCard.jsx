import {
  FaHeart,
  FaBookmark,
  FaStar
} from "react-icons/fa";

function BookCard({ book }) {
  return (
    <div className="book-card">

      <div className="book-image">
        {book.coverImage ? (
          <img
            src={book.coverImage}
            alt={book.title}
          />
        ) : (
          <span>📚</span>
        )}
      </div>

      <h4>{book.title}</h4>

      <p>{book.author}</p>

      <p>{book.genre}</p>

      <p>
        ⭐ {book.rating}/10
      </p>

    </div>
  );
}

export default BookCard;