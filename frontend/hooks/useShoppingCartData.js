import {useNavigate, useParams} from "react-router-dom";
import {useState} from "react";
import { useEffect } from "react";
import { fetchShoppingCarts, deleteProductFromCart } from "../service/shoppingCartService";

export const useShoppingCartData = () => {
    const { cartId } = useParams();
    const navigate = useNavigate();
    const [shoppingCart, setShoppingCart] = useState(null);
    const [error, setError] = useState(null);
    const [showRecommendation, setShowRecommendation] = useState(false);
    const [showAllergens, setShowAllergens] = useState(false);
    const [showNutritionalValue, setShowNutritionalValue] = useState(false);

    const fetchShoppingCartData = async () => {
        try {
            const data = await fetchShoppingCarts(cartId);
            setShoppingCart(data);
        } catch (err) {
            setError("Failed to fetch shopping cart data.");
        }
    };
    useEffect(() => {
        if (cartId) {
            fetchShoppingCartData();
        }
    }, [cartId]);

    const handleDeleteProductFromCart = async (productId) => {
        try {
            const updatedCart = await deleteProductFromCart(cartId, productId);
            if (updatedCart === null) {
                setShoppingCart(prevCart => ({
                    ...prevCart,
                    productsList: prevCart.productsList.filter(product => product.id !== productId)
                }));
                return;
            }
            setShoppingCart(updatedCart);
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
        handleToggleNutritionalValue
    }

}