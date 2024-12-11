import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const FrontPage = () => {
    const [carts, setCarts] = useState([]);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCarts = async () => {
            setError(null);
            try {
                const response = await fetch('http://localhost:8000/cart/all');
                if (!response.ok) {
                    throw new Error('Failed to fetch shopping carts');
                }
                const data = await response.json();
                setCarts(data);
            } catch (err) {
                setError(err.message);
            }
        };

        fetchCarts();
    }, []);

    const handleRedirect = (cartId) => {
        navigate(`/shoppingCart/${cartId}`);
    };

    const handleCreateNewCart = async () => {
        setError(null);
        try {
            const response = await fetch('http://localhost:8000/cart/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Failed to create a new cart');
            }

            const newCart = await response.json();
            handleRedirect(newCart.id);
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div className="container">
            <h3>Existing Shopping Carts</h3>
            {error && <p>{error}</p>}
            <div>
                {carts.map((cart) => (
                    <button key={cart.id} onClick={() => handleRedirect(cart.id)}>
                        Go to Shopping Cart {cart.id}
                    </button>
                ))}
            </div>
            <button onClick={handleCreateNewCart}>
                Create new cart
            </button>
        </div>
    );
};

export default FrontPage;
