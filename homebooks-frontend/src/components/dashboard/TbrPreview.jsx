import { useNavigate } from "react-router-dom";
import BookCard from "../books/BookCard";
import SectionHeader from "./SectionHeader";

function TbrPreview({
  books,
  likedBooks,
  tbrBooks
}) {
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
  likedBooks={likedBooks}
  tbrBooks={tbrBooks}
/>
        ))}
      </div>
    </div>
  );
}

export default TbrPreview;