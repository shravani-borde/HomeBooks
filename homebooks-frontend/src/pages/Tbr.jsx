import {
  useEffect,
  useState
} from "react";

import Layout from "../components/layout/Layout";
import BookGrid from "../components/books/BookGrid";

import {
  getTbrBooks
} from "../api/bookApi";

function Tbr() {
  const [books, setBooks] =
    useState([]);

  useEffect(() => {
    loadBooks();
  }, []);

  const loadBooks =
    async () => {
      try {
        const data =
          await getTbrBooks();

        setBooks(data);
      } catch (error) {
        console.log(error);
      }
    };

  return (
    <Layout>
      <h1>My TBR</h1>

      {books.length === 0 ? (
        <p>
          No books in your TBR.
        </p>
      ) : (
        <BookGrid
          books={books}
        />
      )}
    </Layout>
  );
}

export default Tbr;