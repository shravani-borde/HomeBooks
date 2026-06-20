import { useEffect, useState }
from "react";

import Layout
from "../components/layout/Layout";

import {
  getCurrentUser
} from "../api/userApi";

import {
  getBooks,
  getLikedBooks,
  getTbrBooks,
  getMyRatings
} from "../api/bookApi";

function Profile() {

  const [user,
    setUser] =
    useState(null);

  const [stats,
    setStats] =
    useState({
      books: 0,
      liked: 0,
      tbr: 0,
      ratings: 0
    });

  useEffect(() => {
    loadProfile();
  }, []);

  const loadProfile =
    async () => {

      try {

        const me =
          await getCurrentUser();

        const books =
          await getBooks();

        const liked =
          await getLikedBooks();

        const tbr =
          await getTbrBooks();

        const ratings =
          await getMyRatings();

        setUser(me);

        setStats({
          books:
            books.content.length,

          liked:
            liked.length,

          tbr:
            tbr.length,

          ratings:
            ratings.length
        });

      } catch (error) {
        console.log(error);
      }
    };

  return (
    <Layout>

      <div className="profile-page">

        <div className="profile-card">

          <div
            className="avatar"
          >
            👤
          </div>

          <h2>
            {user?.name}
          </h2>

          <p>
            {user?.email}
          </p>

          <span
            className="role"
          >
            {user?.role}
          </span>

        </div>

        <div className="stats-grid">

          <div
            className="stat-card"
          >
            <h2>
              📚
            </h2>

            <h3>
              {stats.books}
            </h3>

            <p>
              Books
            </p>
          </div>

          <div
            className="stat-card"
          >
            <h2>
              ❤️
            </h2>

            <h3>
              {stats.liked}
            </h3>

            <p>
              Liked
            </p>
          </div>

          <div
            className="stat-card"
          >
            <h2>
              🔖
            </h2>

            <h3>
              {stats.tbr}
            </h3>

            <p>
              My TBR
            </p>
          </div>

          <div
            className="stat-card"
          >
            <h2>
              ⭐
            </h2>

            <h3>
              {stats.ratings}
            </h3>

            <p>
              Ratings
            </p>
          </div>

        </div>

      </div>

    </Layout>
  );
}

export default Profile;