import { useNavigate } from "react-router-dom";
import BookCard from "../books/BookCard";
import SectionHeader from "./SectionHeader";

function PopularBooks({ books }) {
  const navigate = useNavigate();

  return (
    <div>
      <SectionHeader
        title="Popular Books"
        onViewAll={() =>
          navigate("/books")
        }
      />

      <div className="horizontal-books">
        {books.map((book) => (
          <BookCard
            key={book.id}
            book={book}
          />
        ))}
      </div>
    </div>
  );
}

export default PopularBooks;