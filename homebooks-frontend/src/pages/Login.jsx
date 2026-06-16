import { useState } from "react";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = async (e) => {
        e.preventDefault();

        console.log({
            email,
            password
        });
    };

    return (
        <div className="container">

            <h2>Login</h2>

            <form onSubmit={handleLogin}>

                <div>
                    <label>Email</label>

                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="Enter email"
                    />
                </div>

                <br />

                <div>
                    <label>Password</label>

                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="Enter password"
                    />
                </div>

                <br />

                <button type="submit">
                    Login
                </button>

            </form>

        </div>
    );
}

export default Login;