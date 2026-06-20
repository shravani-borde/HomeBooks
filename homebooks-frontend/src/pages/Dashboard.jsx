import { useEffect, useState } from "react";

import Layout from "../components/layout/Layout";
import WelcomeBanner from "../components/dashboard/WelcomeBanner";
import PopularBooks from "../components/dashboard/PopularBooks";
import TbrPreview from "../components/dashboard/TbrPreview";
import LikedPreview from "../components/dashboard/LikedPreview";
import RecommendationSection from "../components/dashboard/RecommendationSection";

import {
  getBooks,
  getTbrBooks,
  getLikedBooks
} from "../api/bookApi";

function Dashboard() {
  const [popularBooks, setPopularBooks] =
    useState([]);

  const [tbrBooks, setTbrBooks] =
    useState([]);

  const [likedBooks, setLikedBooks] =
    useState([]);

  const [loading, setLoading] =
    useState(true);

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard =
    async () => {
      try {
        const popular =
          await getBooks();

        const tbr =
          await getTbrBooks();

        const liked =
          await getLikedBooks();

        setPopularBooks(
          popular.content
        );

        setTbrBooks(
          tbr.slice(0, 3)
        );

        setLikedBooks(
          liked.slice(0, 3)
        );

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
        books={popularBooks}
      />

      <TbrPreview
        books={tbrBooks}
      />

      <LikedPreview
        books={likedBooks}
      />

        <div id="recommendations">
  <RecommendationSection
    books={popularBooks}
  />
</div>

    </Layout>
  );
}

export default Dashboard;