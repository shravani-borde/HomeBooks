import { useNavigate } from "react-router-dom";
import "./Home.css";

function Home() {
  const navigate = useNavigate();

  return (
    <div className="home-container">

      <nav className="navbar">
        <h1>📚 HomeBooks</h1>

        <div>
          <button
            className="nav-btn"
            onClick={() => navigate("/login")}
          >
            Login
          </button>

          <button
            className="nav-btn register-btn"
            onClick={() => navigate("/register")}
          >
            Register
          </button>
        </div>
      </nav>

      <div className="hero">

        <div className="hero-text">
          <h1>
            Discover Your Next
            Favorite Book
          </h1>

          <p>
            Save books to your TBR,
            rate your favorites,
            and receive personalized
            recommendations.
          </p>

          <div className="hero-buttons">

            <button
              onClick={() =>
                navigate("/register")
              }
            >
              Get Started
            </button>

            <button
              className="secondary-btn"
              onClick={() =>
                navigate("/login")
              }
            >
              Login
            </button>

          </div>
        </div>

        <div className="hero-image">
          📚✨
        </div>

      </div>

      <div className="features">

        <div className="feature-card">
          📚
          <h3>Browse Books</h3>
          <p>
            Explore popular books
            from multiple genres.
          </p>
        </div>

        <div className="feature-card">
          🔖
          <h3>Build Your TBR</h3>
          <p>
            Save books and read
            them later.
          </p>
        </div>

        <div className="feature-card">
          ⭐
          <h3>Rate Books</h3>
          <p>
            Share your opinions and
            discover highly rated books.
          </p>
        </div>

        <div className="feature-card">
          ❤️
          <h3>Recommendations</h3>
          <p>
            Receive books tailored
            to your interests.
          </p>
        </div>

      </div>

    </div>
  );
}

export default Home;