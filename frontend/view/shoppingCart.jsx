import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

const ShoppingCart = () => {
    const { cartId } = useParams();
    const navigate = useNavigate();
    const [shoppingCart, setShoppingCart] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchShoppingCart = async () => {
            try {
                const response = await fetch(`http://localhost:8000/cart/${cartId}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch data from the microservice.');
                }
                const data = await response.json();
                setShoppingCart(data);
            } catch (err) {
                setError("Failed to fetch data from the microservice.");
            }
        };

        if (cartId) {
            fetchShoppingCart();
        }
    }, [cartId]);

    const handleNavigateToSearch = () => {
        navigate(`/searchbar/${cartId}`);
    };
    const handleNavigateToRecommendations = () => {
        navigate(`/recommendations/${cartId}`);
    }

    if (error) return <p>{error}</p>;
    if (!shoppingCart || !shoppingCart.productsList) return <p>No shopping cart found</p>;

    return (
        <div>
            <h4>Product List:</h4>
            <button onClick={handleNavigateToSearch}>Go to SearchBar for Cart {cartId}</button>
            <button onClick={handleNavigateToRecommendations}>Get recommendations</button>
            <table>
                <tr>
                    <th>Product Name</th>
                    <th>Calories pr 100g</th>
                    <th>Nutritional Info pr 100g</th>
                    <th>Quantity</th>
                </tr>
                {shoppingCart.productsList.map((product, index) => (
                    <tr key={index}>
                        <td>{product.productName}</td>
                        <td>{product.calories}</td>
                        <td>
                            <ul>
                                {product.nutritionalInfo && Array.isArray(product.nutritionalInfo) ? (
                                    product.nutritionalInfo.map((nutrient, nutrientIndex) => (
                                        <li key={nutrientIndex}>
                                            {nutrient.nutrientName}: {nutrient.nutrientValue}
                                        </li>
                                    ))
                                ) : (
                                    <li>No nutritional info available</li>
                                )}
                            </ul>
                        </td>
                        <td>{product.quantity}</td>
                    </tr>
                ))}
            </table>

        </div>
    );
};

export default ShoppingCart;
