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
  getLikedBooks,
  getRecommendations
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

const [recommendations,
  setRecommendations] =
  useState([]);

useEffect(() => {
  loadDashboard();
}, []);

const loadDashboard =
  async () => {
    try {

      const [
  booksData,
  likedData,
  tbrData,
  recommendationData
] = await Promise.all([
  getBooks(),
  getLikedBooks(),
  getTbrBooks(),
  getRecommendations()
]);

setPopularBooks(booksData.content);
setLikedBooks(likedData);
setTbrBooks(tbrData);
setRecommendations(recommendationData);

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

        <RecommendationSection
  books={recommendations}
  likedBooks={likedBooks}
  tbrBooks={tbrBooks}
      />

    </Layout>
  );
}

export default Dashboard;