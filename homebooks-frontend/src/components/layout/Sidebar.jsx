import {
  FaHome,
  FaBook,
  FaHeart,
  FaStar,
  FaUser,
  FaCog,
  FaSignOutAlt,
  FaBookmark
} from "react-icons/fa";

import { useNavigate } from "react-router-dom";
import useAuth from "../../hooks/useAuth";

function Sidebar() {
  const navigate = useNavigate();
  const { logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  const goToRecommendations = () => {
    navigate("/dashboard");

    setTimeout(() => {
      document
        .getElementById("recommendations")
        ?.scrollIntoView({
          behavior: "smooth"
        });
    }, 100);
  };

  return (
    <div className="sidebar">

      <h2 className="logo">
        📚 HomeBooks
      </h2>

      <div className="menu">

        <button
          onClick={() =>
            navigate("/dashboard")
          }
        >
          <FaHome />
          Dashboard
        </button>

        <button
          onClick={() =>
            navigate("/books")
          }
        >
          <FaBook />
          Books
        </button>

        <button
          onClick={() =>
            navigate("/my-tbr")
          }
        >
          <FaBookmark />
          My TBR
        </button>

        <button
          onClick={() =>
            navigate("/liked")
          }
        >
          <FaHeart />
          Liked Books
        </button>

        <button
          onClick={() =>
            navigate("/ratings")
          }
        >
          <FaStar />
          My Ratings
        </button>

        <button
          onClick={goToRecommendations}
        >
          ⭐ Recommendations
        </button>

        <button
          onClick={() =>
            navigate("/profile")
          }
        >
          <FaUser />
          Profile
        </button>

        <button
          onClick={() =>
            navigate("/settings")
          }
        >
          <FaCog />
          Settings
        </button>

        <button
          onClick={handleLogout}
        >
          <FaSignOutAlt />
          Logout
        </button>

      </div>
    </div>
  );
}

export default Sidebar;