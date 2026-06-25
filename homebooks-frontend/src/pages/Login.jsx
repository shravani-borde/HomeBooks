import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../api/authApi";
import "../styles/Auth.css";

function Login() {

  const navigate =
    useNavigate();

  const [email, setEmail] =
    useState("");

  const [password,
    setPassword] =
    useState("");

  const handleSubmit =
    async (e) => {

      e.preventDefault();

      try {
        const response =
          await loginUser({
            email,
            password
          });

        localStorage.setItem(
          "token",
          response
        );

        navigate(
          "/dashboard"
        );

      } catch (error) {
        alert(
          "Invalid credentials"
        );
      }
    };

  return (
    <div className="auth-page">

      <div className="auth-left">
        <div className="auth-content">
          <h1>📚 HomeBooks</h1>

          <p>
            Discover books,
            build your TBR,
            rate your favourites,
            and receive
            personalised
            recommendations.
          </p>
        </div>
      </div>

      <div className="auth-right">

        <form
          className="auth-card"
          onSubmit={
            handleSubmit
          }
        >

          <h2>
            Welcome Back
          </h2>

          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) =>
              setEmail(
                e.target.value
              )
            }
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) =>
              setPassword(
                e.target.value
              )
            }
          />

          <button
            type="submit"
          >
            Login
          </button>

          <p
            className="auth-link"
          >
            Don't have an account?

            <span
              onClick={() =>
                navigate(
                  "/register"
                )
              }
            >
              {" "}
              Register
            </span>
          </p>

        </form>

      </div>

    </div>
  );
}

export default Login;