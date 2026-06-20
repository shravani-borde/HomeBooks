import { useEffect, useState } from "react";
import Layout from "../components/layout/Layout";
import { getMyRatings } from "../api/bookApi";

function Ratings() {
  const [ratings, setRatings] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadRatings();
  }, []);

  const loadRatings = async () => {
    try {
      const data = await getMyRatings();
      setRatings(data);
    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <Layout>
        <h2>Loading...</h2>
      </Layout>
    );
  }

  return (
    <Layout>
      <h1 className="page-title">
        My Ratings
      </h1>

      {ratings.length === 0 ? (
        <p>No ratings yet.</p>
      ) : (
        <div className="books-grid">
          {ratings.map((rating) => (
            <div
              key={rating.id}
              className="book-card"
            >
              <div className="book-image">
                {rating.book.coverImage ? (
                  <img
                    src={rating.book.coverImage}
                    alt={rating.book.title}
                    onError={(e) =>
                      (e.target.style.display =
                        "none")
                    }
                  />
                ) : (
                  <span>📚</span>
                )}
              </div>

              <h4>{rating.book.title}</h4>

              <p>{rating.book.author}</p>

              <p>{rating.book.genre}</p>

              <p>
                ⭐ {rating.score}/10
              </p>
            </div>
          ))}
        </div>
      )}
    </Layout>
  );
}

export default Ratings;