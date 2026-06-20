import {
  FaHeart,
  FaBookmark,
  FaStar
} from "react-icons/fa";

import {
  addToTbr,
  likeBook,
  rateBook
} from "../../api/bookApi";

import { useState } from "react";

function BookCard({ book }) {

  const handleTbr = async () => {
    try {
      await addToTbr(book.id);
      alert("Added to TBR!");
    } catch (error) {
      console.log(error);
      alert("Failed to add.");
    }
  };

  const handleLike = async () => {
    try {
      await likeBook(book.id);
      alert("Book liked!");
    } catch (error) {
      console.log(error);
      alert("Failed to like.");
    }
  };

  const [showRating, setShowRating] =
  useState(false);

const [hoveredRating,
  setHoveredRating] =
  useState(0);

const [selectedRating,
  setSelectedRating] =
  useState(0);

  const handleRating =
  async (score) => {
    try {
      await rateBook(
        book.id,
        score
      );

      setSelectedRating(score);
      setShowRating(false);

      alert("Rating saved!");
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="book-card">

      <div className="book-image">
        {book.coverImage ? (
          <img
            src={book.coverImage}
            alt={book.title}
            onError={(e) =>
              (e.target.style.display =
                "none")
            }
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

      <div className="book-actions">

        {showRating && (
  <div className="star-rating">

    {[...Array(10)].map(
      (_, index) => {
        const value =
          index + 1;

        return (
          <FaStar
            key={value}
            className="rating-star"
            color={
              value <=
              (
                hoveredRating ||
                selectedRating
              )
                ? "#ffc107"
                : "#e4e5e9"
            }
            size={25}
            onMouseEnter={() =>
              setHoveredRating(
                value
              )
            }
            onMouseLeave={() =>
              setHoveredRating(0)
            }
            onClick={() =>
              handleRating(
                value
              )
            }
          />
        );
      }
    )}

  </div>
)}

        <FaHeart
          onClick={handleLike}
        />

        <FaBookmark
          onClick={handleTbr}
        />

        <FaStar
  onClick={() =>
    setShowRating(
      !showRating
    )
  }
/>

      </div>

    </div>
  );
}

export default BookCard;