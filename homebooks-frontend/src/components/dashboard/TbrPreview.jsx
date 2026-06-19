import { useNavigate } from "react-router-dom";
import BookCard from "../books/BookCard";
import SectionHeader from "./SectionHeader";

function TbrPreview({ books }) {
  const navigate = useNavigate();

  return (
    <div>
      <SectionHeader
        title="My TBR"
        onViewAll={() =>
          navigate("/my-tbr")
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

export default TbrPreview;