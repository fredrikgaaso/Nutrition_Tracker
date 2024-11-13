import React, { useState, useEffect } from 'react';

const ShoppingCart = ({ userId }) => {
    const [shoppingCart, setShoppingCart] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchShoppingCart = async () => {
            try {
                const response = await fetch(`http://localhost:8000/cart/${userId}`);
                const data = await response.json();
                console.log(data);  // Log the response to check its structure
                setShoppingCart(data);
                setLoading(false);
            } catch (err) {
                setError("Failed to fetch data from the microservice.");
                setLoading(false);
            }
        };

        if (userId) {
            fetchShoppingCart();
        }
    }, [userId]);  // Re-run the effect when userId changes

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;
    if (!shoppingCart || !shoppingCart.productsList) return <p>No shopping cart found</p>;

    return (
        <div>
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
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ShoppingCart;
