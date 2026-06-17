import { Link, useNavigate } from "react-router-dom";

function Navbar() {

    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/login");
    };

    return (
        <nav>

            <Link to="/books">Books</Link>{" | "}

            <Link to="/tbr">My TBR</Link>{" | "}

            <Link to="/likes">Liked Books</Link>{" | "}

            <button onClick={handleLogout}>
                Logout
            </button>

        </nav>
    );
}

export default Navbar;