import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Books from "./pages/Books";
import Tbr from "./pages/Tbr";
import Likes from "./pages/Likes";

function App() {
  return (
    <BrowserRouter>

      <Routes>

        <Route path="/" element={<Login />} />

        <Route path="/login" element={<Login />} />

        <Route path="/register" element={<Register />} />

        <Route path="/books" element={<Books />} />

        <Route path="/tbr" element={<Tbr />} />

        <Route path="/likes" element={<Likes />} />
      </Routes>

    </BrowserRouter>
  );
}

export default App;