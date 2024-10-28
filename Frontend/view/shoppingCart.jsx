import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ShoppingCart = () => {
    const [shoppingCart, setShoppingCart] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchShoppingCart = async () => {
            try {
                const response = await axios.get('http://localhost:8081/shop/cart/1');
                setShoppingCart(response.data);
                setLoading(false);
            } catch (err) {
                setError("Failed to fetch data from the microservice.");
                setLoading(false);
            }
        };

        fetchShoppingCart();
    }, []);

    if (loading) return <p>Loading...</p>;

    if (error) return <p>{error}</p>;

    if (!shoppingCart) return <p>No shopping cart found</p>;

    return (
        <div>
            <h2>User: {shoppingCart.user.name}</h2>
            <h3>Wallet: {shoppingCart.user.wallet}</h3>

            <h4>Product List:</h4>
            <table>
                <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Calories</th>
                    <th>Nutritional Info</th>
                </tr>
                </thead>
                <tbody>
                {shoppingCart.productList.map((product) => (
                    <tr key={product.id}>
                        <td>{product.productName}</td>
                        <td>{product.calories}</td>
                        <td>
                            <ul>
                                {product.nutritionalInfo.map((nutrient) => (
                                    <li key={nutrient.nutrientName}>
                                        {nutrient.nutrientName}: {nutrient.nutrientValue}
                                    </li>
                                ))}
                            </ul>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ShoppingCart;
