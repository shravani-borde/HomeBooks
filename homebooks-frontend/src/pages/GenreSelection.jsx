import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
  getGenres,
  getFavoriteGenres,
  saveFavoriteGenres
} from "../api/bookApi";

import "../styles/GenreSelection.css";

function GenreSelection() {

  const navigate = useNavigate();

  const [genres, setGenres] = useState([]);

  const [selectedGenres,
    setSelectedGenres] = useState([]);

  useEffect(() => {
    loadGenres();
  }, []);

  const loadGenres = async () => {

    try {

      const allGenres =
        await getGenres();

      const favourites =
        await getFavoriteGenres();

      setGenres(allGenres);
      setSelectedGenres(favourites);

    } catch (err) {
      console.log(err);
    }
  };

  const toggleGenre = (genre) => {

    if (
      selectedGenres.includes(genre)
    ) {

      setSelectedGenres(
        selectedGenres.filter(
          g => g !== genre
        )
      );

    } else {

      setSelectedGenres([
        ...selectedGenres,
        genre
      ]);

    }

  };

  const handleSave = async () => {

    try {

      await saveFavoriteGenres(
        selectedGenres
      );

      navigate("/dashboard");

    } catch (err) {

      console.log(err);

      alert("Couldn't save genres.");

    }

  };

  return (

    <div className="genre-page">

      <div className="genre-card">

        <h1>
          Choose your favourite genres
        </h1>

        <p>
          Select as many as you like.
        </p>

        <div className="genre-grid">

          {genres.map((genre) => (

            <button

              key={genre}

              className={
                selectedGenres.includes(genre)
                  ? "genre-btn selected"
                  : "genre-btn"
              }

              onClick={() =>
                toggleGenre(genre)
              }

            >
              {genre}
            </button>

          ))}

        </div>

        <button
          className="save-btn"
          onClick={handleSave}
        >
          Continue
        </button>

      </div>

    </div>

  );

}

export default GenreSelection;