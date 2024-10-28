import React, { useState, useEffect } from 'react';
import axios from 'axios';

const SimpleLogin = () => {
    const [user, setUser] = useState([]);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [error, setError] = useState(null);

    useEffect (() => {
        const fetchUsers = async () => {
            try {
                const response = await axios.get('http://localhost:8086/user/all');
                setUser(response.data);
            } catch (err) {
                setError("Failed to fetch data from the microservice.");
            }
        };

        fetchUsers();
    }, []);

    const handleLogin = (e) => {
        e.preventDefault();

        const user = users.find((user) => user.name === username && user.password === password)

        if (user){
            setMessage("Login successful!")
        }
        else {
            setMessage("Invalid username or password")
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <div>
                    <label>Username:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Login</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default SimpleLogin;
