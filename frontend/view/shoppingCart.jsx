import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Recommendation from "./recommendation";
import Allergen from "./allergen";
import desiredNutrition from "./desiredNutrition";
import DesiredNutrition from "./desiredNutrition";

const ShoppingCart = () => {
    const { cartId } = useParams();
    const navigate = useNavigate();
    const [shoppingCart, setShoppingCart] = useState(null);
    const [error, setError] = useState(null);
    const [showRecommendation, setShowRecommendation] = useState(false);
    const [showAllergens, setShowAllergens] = useState(false);
    const [showNutritionalValue, setShowNutritionalValue] = useState(false);
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

    const handleDeleteProduct = async (productId) => {
        try {
            const response = await fetch(`http://localhost:8000/cart/remove`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    cartId,
                    productId,
                }),
            });

            if (!response.ok) {
                throw new Error('Failed to delete product');
            }
            const text = await response.text();
            const updatedCart = text ? JSON.parse(text) : null;

            if (updatedCart) {
                setShoppingCart(updatedCart);
            } else {
                setShoppingCart(prevCart => ({
                    ...prevCart,
                    productsList: prevCart.productsList.filter(product => product.id !== productId)
                }));
            }
        } catch (err) {
            console.error('Failed to delete product:', err);
            setError('Failed to delete product');
        }
    }

    const handleNavigateToSearch = () => {
        navigate(`/searchbar/${cartId}`);
    };
    const handleNavigateToFrontPage = () => {
        navigate(`/`);
    }
    const handleToggleRecommendation = () => {
        setShowRecommendation(Recommendation => !Recommendation);
    }
    const handleToggleAllergens = () => {
        setShowAllergens(showAllergens => !showAllergens);
    }
    const handleToggleNutritionalValue = () => {
        setShowNutritionalValue(showNutritionalValue => !showNutritionalValue);
    }

    if (error) return <p>{error}</p>;
    if (!shoppingCart || !shoppingCart.productsList) return <p>No shopping cart found</p>;

    return (
        <div>
            <button onClick={handleNavigateToFrontPage}>Go back to front page</button>
            <button onClick={handleNavigateToSearch}>Go to SearchBar for Cart {cartId}</button>
            <button onClick={handleToggleAllergens}>{showAllergens ? 'Hide Allergens' : 'Add Allergens'}</button>
            <button onClick={handleToggleRecommendation}>{showRecommendation ? 'Hide Recommendations' : 'Get Recommendations'}</button>
            <button onClick={handleToggleNutritionalValue}>{showNutritionalValue ? 'Hide Nutritional Value' : 'Add nutritional value'}</button>
            {showRecommendation && <Recommendation cartId={cartId}/>}
            {showAllergens && <Allergen cartId={cartId}/>}
            {showNutritionalValue && <DesiredNutrition cartId={cartId}/>}

            <h4>Product List:</h4>
            <table>
                <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Calories pr 100g</th>
                    <th>Nutritional Info pr 100g</th>
                    <th>Quantity</th>
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
                        <td>{product.quantity}</td>
                        <td><button onClick={() => handleDeleteProduct(product.id)}>Remove Product</button></td>
                    </tr>
                ))}
                </tbody>
            </table>

        </div>
    );
};

export default ShoppingCart;