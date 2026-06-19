import { useEffect, useState } from "react";

import Layout from "../components/layout/Layout";
import SearchBar from "../components/common/SearchBar";
import BookGrid from "../components/books/BookGrid";
import Pagination from "../components/common/Pagination";
import {
  useSearchParams
} from "react-router-dom";

import {
  getBooks,
  searchBooks
} from "../api/bookApi";

function Books() {
  const [books, setBooks] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] =
    useState(0);

  const [search, setSearch] =
    useState("");
    const [searchParams] =
  useSearchParams();

    const query =
  searchParams.get("q");

    useEffect(() => {
  if (query) {
    handleSearch(query);
  } else {
    loadBooks();
  }
}, [page, query]);

  const loadBooks = async () => {
    try {
      const data =
        await getBooks(page);

      setBooks(data.content);
      setTotalPages(
        data.totalPages
      );
    } catch (error) {
      console.log(error);
    }
  };

  const handleSearch = async (
  keyword
) => {
  try {
    const data =
      await searchBooks(
        keyword
      );

    setBooks(data);
  } catch (error) {
    console.log(error);
  }
};

  return (
    <Layout>
      <h1>Books</h1>

      <BookGrid books={books} />

      {!search && (
        <Pagination
          page={page}
          totalPages={totalPages}
          setPage={setPage}
        />
      )}
    </Layout>
  );
}

export default Books;