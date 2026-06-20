import {
  useEffect,
  useState
} from "react";

import Layout from "../components/layout/Layout";
import BookGrid from "../components/books/BookGrid";

import {
  getLikedBooks
} from "../api/bookApi";

function LikedBooks() {
  const [books, setBooks] =
    useState([]);

  useEffect(() => {
    loadBooks();
  }, []);

  const loadBooks =
    async () => {
      try {
        const data =
          await getLikedBooks();

        setBooks(data);
      } catch (error) {
        console.log(error);
      }
    };

  return (
    <Layout>
      <h1>Liked Books</h1>

      {books.length === 0 ? (
        <p>
          No liked books yet.
        </p>
      ) : (
        <BookGrid
          books={books}
        />
      )}
    </Layout>
  );
}

export default LikedBooks;