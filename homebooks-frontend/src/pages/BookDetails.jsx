import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

import "../styles/BookDetails.css";

import {
  getBookById,
  getLikedBooks,
  getTbrBooks,
  addLikedBook,
  removeLikedBook,
  addToTbr,
  removeFromTbr,
  likeBook,
  rateBook
} from "../api/bookApi";

function BookDetails() {

    const { id } = useParams();
    const navigate = useNavigate();

    const [book, setBook] = useState(null);
    const [loading, setLoading] = useState(true);

    const [rating, setRating] = useState(0);

    const [liked, setLiked] = useState(false);
const [saved, setSaved] = useState(false);

    useEffect(() => {
  loadBook();
  loadUserState();
}, [id]);

    const loadUserState = async () => {
  try {
    const [likedData, tbrData] = await Promise.all([
      getLikedBooks(),
      getTbrBooks()
    ]);

    setLiked(
      likedData.some(
        book => book.id === Number(id)
      )
    );

    setSaved(
      tbrData.some(
        book => book.id === Number(id)
      )
    );

  } catch (error) {
    console.log("Failed to load user state:", error);
  }
};

    const loadBook = async () => {

        try {

            const data =
                await getBookById(id);

            setBook(data);

        } catch (error) {

            console.log(error);

        } finally {

            setLoading(false);

        }
    };

    const handleLike = async () => {
  try {

    if (liked) {
      await removeLikedBook(book.id);
      setLiked(false);
    } else {
      await addLikedBook(book.id);
      setLiked(true);
    }

  } catch (error) {
    console.log(error);
  }
};

    const handleTbr = async () => {
  try {

    if (saved) {
      await removeFromTbr(book.id);
      setSaved(false);
    } else {
      await addToTbr(book.id);
      setSaved(true);
    }

  } catch (error) {
    console.log(error);
  }
};

    const handleRating = async (score) => {

        try {

            await rateBook(
                book.id,
                score
            );

            setRating(score);

            alert("Rating saved ⭐");

        } catch (error) {

            console.log(error);

        }
    };

    if (loading) {

        return (
            <div className="details-loading">
                Loading book...
            </div>
        );
    }

    if (!book) {

        return (
            <div className="details-loading">
                Book not found.
            </div>
        );
    }

    return (

        <div className="book-details-page">

            <button
                className="back-button"
                onClick={() =>
                    navigate(-1)
                }
            >
                ← Back
            </button>

            <div className="book-details-card">

                <div className="details-cover">

                    {book.coverImage ? (

                        <img
                            src={book.coverImage}
                            alt={book.title}
                        />

                    ) : (

                        <div className="no-cover">
                            📚
                        </div>

                    )}

                </div>

                <div className="details-content">

                    <h1>
                        {book.title}
                    </h1>

                    <h3>
                        by {book.author}
                    </h3>

                    <span className="genre">
                        {book.genre}
                    </span>

                    <p className="book-rating">
                        ⭐ {book.rating}/10
                    </p>

                    <p className="description">
                        {book.description ||
                            "No description available."}
                    </p>

                    <div className="details-actions">

                        <button onClick={handleLike}>
                          {liked ? "❤️ Liked" : "❤️ Like"}
                        </button>

                        <button onClick={handleTbr}>
                          {saved
                            ? "📚 In TBR"
                            : "📚 Add to TBR"}
                        </button>

                    </div>

                    <div className="rating-section">

                        <h3>
                            Rate this book
                        </h3>

                        <div className="rating-buttons">

                            {[1,2,3,4,5,6,7,8,9,10]
                                .map((value) => (

                                <button
                                    key={value}
                                    className={
                                        value <= rating
                                            ? "rating-selected"
                                            : ""
                                    }
                                    onClick={() =>
                                        handleRating(value)
                                    }
                                >
                                    {value}
                                </button>

                            ))}

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}

export default BookDetails;