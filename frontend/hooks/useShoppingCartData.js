import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import { fetchShoppingCarts, deleteProductFromCart } from "../service/shoppingCartService";

export const useShoppingCartData = () => {
    const { cartId } = useParams();
    const navigate = useNavigate();
    const [shoppingCart, setShoppingCart] = useState(null);
    const [error, setError] = useState(null);
    const [showRecommendation, setShowRecommendation] = useState(false);
    const [showAllergens, setShowAllergens] = useState(false);
    const [showNutritionalValue, setShowNutritionalValue] = useState(false);
    const [productsList, setProductsList] = useState([]);
    const [calculatedNutritionalValue, setCalculatedNutritionalValue] = useState([]);
    const [calculatedCalories, setCalculatedCalories] = useState(0);
    const fetchShoppingCartData = async () => {
        try {
            const data = await fetchShoppingCarts(cartId);
            setShoppingCart(data);
            setProductsList(data.productsList);
        } catch (err) {
            setError("Failed to fetch shopping cart data.");
        }
    };

    useEffect(() => {
        if (productsList.length>0) {
            calculateNutritionalValue(productsList);
           const totalCalories=  calculateTotalCalories(productsList);
           setCalculatedCalories(totalCalories);
            console.log(calculatedNutritionalValue)
        }
    }, [productsList]);

    const calculateNutritionalValue = (productList) => {
        console.log("calculateNutritionalValue called with productList:", productList);
        if (!productList || productList.length === 0) {
            setCalculatedNutritionalValue([]);
            return;
        }

        const totalNutritionalValue = productList.reduce((acc, product) => {
            console.log("called")
            if (product.nutritionalInfo && Array.isArray(product.nutritionalInfo)) {
                product.nutritionalInfo.forEach(nutrient => {
                    acc[nutrient.nutrientName] = (acc[nutrient.nutrientName] || 0) + Math.round(nutrient.nutrientValue * product.quantity);
                });
            }
            return acc;
        }, {});

        setCalculatedNutritionalValue(totalNutritionalValue);

    };
    const calculateTotalCalories = (productList) => {
        if (!productList || productList.length === 0) {
            return 0;
        }
        return productList.reduce((total, product) => {
            return total + (product.calories * product.quantity);
        }, 0);


    }
    useEffect(() => {
        if (cartId) {
            fetchShoppingCartData();
        }
    }, [cartId]);

    const handleDeleteProductFromCart = async (productId) => {
        try {
            const updatedCart = await deleteProductFromCart(cartId, productId);
            if (updatedCart === null) {
                const updatedProductsList = shoppingCart.productsList.filter(product => product.id !== productId);
                setShoppingCart(prevCart => ({
                    ...prevCart,
                    productsList: prevCart.productsList.filter(product => product.id !== productId)
                }));
                setProductsList(updatedProductsList);
                calculateNutritionalValue(updatedProductsList);
                return;
            }
            setShoppingCart(updatedCart);
            setProductsList(updatedCart.productsList);
            calculateNutritionalValue(updatedCart.productsList);
        } catch (err) {
            setError("Failed to delete product from cart.");
        }
    }
    const handleNavigateToProduct = () => {
        navigate(`/product/${cartId}`);
    }
    const handleNavigateToFrontpage = () => {
        navigate("/");
    }
    const handleToggleRecommendation = () => {
        setShowRecommendation(Recommendation => !showRecommendation);
    }
    const handleToggleAllergens = () => {
        setShowAllergens(allergens => !showAllergens);
    }
    const handleToggleNutritionalValue = () => {
        setShowNutritionalValue(nutritionalValue => !showNutritionalValue);
    }
    return {
        shoppingCart,
        cartId,
        setShoppingCart,
        error,
        setError,
        showRecommendation,
        setShowRecommendation,
        showAllergens,
        setShowAllergens,
        showNutritionalValue,
        setShowNutritionalValue,
        fetchShoppingCartData,
        handleDeleteProductFromCart,
        handleNavigateToProduct,
        handleNavigateToFrontpage,
        handleToggleRecommendation,
        handleToggleAllergens,
        handleToggleNutritionalValue,
        calculatedNutritionalValue,
        calculatedCalories,
    }

}