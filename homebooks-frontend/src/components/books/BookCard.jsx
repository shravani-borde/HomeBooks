import {
  FaHeart,
  FaBookmark,
  FaStar
} from "react-icons/fa";

import {
  addLikedBook,
  removeLikedBook,
  addToTbr,
  removeFromTbr,
  rateBook
} from "../../api/bookApi";

import { useState } from "react";

function BookCard({
  book,
  likedBooks = [],
  tbrBooks = []
}) {

  const [liked, setLiked] =
    useState(
      likedBooks.some(
        b => b.id === book.id
      )
    );

  const [saved, setSaved] =
    useState(
      tbrBooks.some(
        b => b.id === book.id
      )
    );

    const [loadingLike,
setLoadingLike] =
  useState(false);

  const handleLike = async () => {
  if (loadingLike) return;

  setLoadingLike(true);

  try {
    if (liked) {
      await removeLikedBook(
        book.id
      );
      setLiked(false);
    } else {
      await addLikedBook(
        book.id
      );
      setLiked(true);
    }
  } catch (error) {
    console.log(error);
  } finally {
    setLoadingLike(false);
  }
};

    const [loadingTbr,
setLoadingTbr] =
  useState(false);

  const handleTbr = async () => {
  if (loadingTbr) return;

  setLoadingTbr(true);

  try {
    if (saved) {
      await removeFromTbr(book.id);
      setSaved(false);
    } else {
      await addToTbr(book.id);
      setSaved(true);
    }
  } finally {
    setLoadingTbr(false);
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
  className="action-icon"
  color={
    liked
      ? "red"
      : "gray"
  }
  onClick={handleLike}
/>

        <FaBookmark
  className="action-icon"
  color={
    saved
      ? "#6d5dfc"
      : "gray"
  }
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