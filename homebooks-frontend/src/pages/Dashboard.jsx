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

      const [
        booksData,
        likedData,
        tbrData
      ] = await Promise.all([
        getBooks(),
        getLikedBooks(),
        getTbrBooks()
      ]);

      setPopularBooks(
        booksData.content
      );

      setLikedBooks(
        likedData
      );

      setTbrBooks(
        tbrData
      );

    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false);
    }
  };


  return (
    <Layout>

      <WelcomeBanner />

      <PopularBooks
        books={popularBooks}
  likedBooks={likedBooks}
  tbrBooks={tbrBooks}
      />

      <TbrPreview
        books={tbrBooks}
  likedBooks={likedBooks}
  tbrBooks={tbrBooks}
      />

      <LikedPreview
        books={likedBooks}
  likedBooks={likedBooks}
  tbrBooks={tbrBooks}
      />

        <div id="recommendations">
  <RecommendationSection
    books={popularBooks}
  likedBooks={likedBooks}
  tbrBooks={tbrBooks}
  />
</div>

    </Layout>
  );
}

export default Dashboard;