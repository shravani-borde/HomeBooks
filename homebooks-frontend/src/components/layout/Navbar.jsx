import { FaSearch } from "react-icons/fa";
import {
  useNavigate
} from "react-router-dom";
import { useState } from "react";

function Navbar() {
  const navigate = useNavigate();

  const [search, setSearch] =
    useState("");

  const handleSearch = (e) => {
    if (
      e.key === "Enter" &&
      search.trim()
    ) {
      navigate(
        `/books?q=${search}`
      );
    }
  };

  return (
    <div className="navbar">
      <div className="search-box">
        <FaSearch />

        <input
          type="text"
          placeholder="Search books..."
          value={search}
          onChange={(e) =>
            setSearch(
              e.target.value
            )
          }
          onKeyDown={
            handleSearch
          }
        />
      </div>
    </div>
  );
}

export default Navbar;