import { useEffect, useState } from "react";

import Layout from "../components/layout/Layout";
import WelcomeBanner from "../components/dashboard/WelcomeBanner";
import PopularBooks from "../components/dashboard/PopularBooks";
import TbrPreview from "../components/dashboard/TbrPreview";
import LikedPreview from "../components/dashboard/LikedPreview";
import RecommendationSection from "../components/dashboard/RecommendationSection";

import { getBooks } from "../api/bookApi";

function Dashboard() {
  const [books, setBooks] =
    useState([]);

  const [loading, setLoading] =
    useState(true);

  useEffect(() => {
    loadBooks();
  }, []);

  const loadBooks = async () => {
    try {
      const data =
        await getBooks();

      setBooks(data.content);
    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <Layout>
        <h2>Loading...</h2>
      </Layout>
    );
  }

  return (
    <Layout>

      <WelcomeBanner />

      <PopularBooks
        books={books}
      />

      <TbrPreview
        books={books.slice(0, 3)}
      />

      <LikedPreview
        books={books.slice(0, 3)}
      />

      <RecommendationSection
        books={books}
      />

    </Layout>
  );
}

export default Dashboard;