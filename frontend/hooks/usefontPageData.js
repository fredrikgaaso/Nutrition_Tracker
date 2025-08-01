import {fetchCarts, createNewCart, deleteCart} from "../service/frontPageService";
import {useEffect, useState, useCallback} from "react";
import {useNavigate} from "react-router-dom";
export const useFontPageData = () => {
    const [carts, setCarts] = useState([]);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const fetchCartsCall = useCallback(async () => {
        setError(null);
        try {
            const data = await fetchCarts();
            setCarts(data);
            console.log('Fetched shopping carts:', data);
        } catch (err) {
            setError(err.message);
        }
    }, []);
    const handleRedirect = (cartId) => {
        navigate(`/shoppingCart/${cartId}`);
    };
    const handleCreateNewCart = async (cartName) => {
        setError(null);
        try {
            const newCart = await createNewCart(cartName);
            handleRedirect(newCart.id);
        } catch (err) {
            setError(err.message);
        }
    };
    const handleDeleteCart = async (cartId) => {
        setError(null);
        try {
            await deleteCart(cartId);
            setCarts(carts.filter(cart => cart.id !== cartId));
        } catch (err) {
            setError(err.message);
        }
    }
    useEffect(() => {
        fetchCartsCall();
    }, [fetchCartsCall]);

    return {
        carts,
        setCarts,
        error,
        setError,
        fetchCartsCall,
        handleRedirect,
        handleCreateNewCart,
        handleDeleteCart
    };
}