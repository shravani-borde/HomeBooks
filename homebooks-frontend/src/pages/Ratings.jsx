import {
  useEffect,
  useState
} from "react";

import Layout from
"../components/layout/Layout";

import {
  getMyRatings
} from "../api/bookApi";

function Ratings() {
  const [ratings,
    setRatings] =
    useState([]);

  useEffect(() => {
    loadRatings();
  }, []);

  const loadRatings =
    async () => {
      try {
        const data =
          await getMyRatings();

        setRatings(data);

      } catch (error) {
        console.log(error);
      }
    };

  return (
    <Layout>

      <h1>
        My Ratings
      </h1>

      {ratings.map(
        (rating) => (
          <div
            key={rating.id}
          >
            <h3>
              {
                rating
                .book
                .title
              }
            </h3>

            <p>
              ⭐
              {
                rating
                .score
              }
              /10
            </p>
          </div>
        )
      )}

    </Layout>
  );
}

export default Ratings;