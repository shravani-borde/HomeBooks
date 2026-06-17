import { useEffect, useState } from "react";
import { getAllBooks } from "../services/bookService";
import Navbar from "../components/Navbar";

function Books() {

    const [books, setBooks] = useState([]);

    useEffect(() => {

        loadBooks();

    }, []);

    const loadBooks = async () => {

        try {

            const data =
                await getAllBooks();

            setBooks(data);

        } catch (error) {

            console.error(error);

        }
    };

    return (
        <div>
            <div>

        <Navbar />

        <h2>Books</h2>

        {/* books */}

    </div>

            <h2>Books</h2>

            {
                books.map((book) => (

                    <div key={book.id}>

                        <h3>{book.title}</h3>

                        <p>
                            Author: {book.author}
                        </p>

                        <p>
                            Genre: {book.genre}
                        </p>

                        <hr />

                    </div>

                ))
            }

        </div>
    );
}

export default Books;