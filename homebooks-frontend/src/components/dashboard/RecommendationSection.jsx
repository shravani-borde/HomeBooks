import { useEffect, useRef, useState } from "react";
import BookCard from "../books/BookCard";


function RecommendationSection({
  books,
  likedBooks,
  tbrBooks
}) {

  const [visibleCount, setVisibleCount] =
    useState(40);

  const loaderRef = useRef(null);


  // Reset when recommendations change
  useEffect(() => {
    setVisibleCount(40);
  }, [books]);


  // Load more books when user reaches the bottom
  useEffect(() => {

    if (!loaderRef.current) return;

    const observer = new IntersectionObserver(
      (entries) => {

        if (
          entries[0].isIntersecting &&
          visibleCount < books.length
        ) {

          setVisibleCount(
            previous =>
              Math.min(
                previous + 40,
                books.length
              )
          );
        }

      },
      {
        rootMargin: "500px"
      }
    );


    observer.observe(loaderRef.current);


    return () => {
      observer.disconnect();
    };

  }, [
    books.length,
    visibleCount
  ]);


  if (!books || books.length === 0) {

    return (
      <section className="recommendation-section">

        <h2>
          Recommended For You
        </h2>

        <p>
          Like a few books to get
          personalized recommendations.
        </p>

      </section>
    );
  }


  const visibleBooks =
    books.slice(
      0,
      visibleCount
    );


  return (

    <section className="recommendation-section">

      <h2>
        Recommended For You
      </h2>


      <div className="book-grid">

        {visibleBooks.map(book => (

          <BookCard
            key={book.id}
            book={book}
            likedBooks={likedBooks}
            tbrBooks={tbrBooks}
          />

        ))}

      </div>


      {/* Invisible trigger for loading more */}
      {visibleCount < books.length && (

        <div
          ref={loaderRef}
          className="recommendation-loader"
        >
          Loading more recommendations...
        </div>

      )}

    </section>
  );
}


export default RecommendationSection;