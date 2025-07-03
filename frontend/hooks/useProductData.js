import {useEffect, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import { fetchProducts, handleAddProduct, fetchProductListFromApi } from '../service/productService';

export const useProductData = () => {
    const { cartId } = useParams();
    const navigate = useNavigate();
    const [searchTerm, setSearchTerm] = useState("");
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [addedProducts, setAddedProducts] = useState(new Set());
    const [quantity, setQuantity] = useState({});

    const filteredProducts = products.filter((product) =>
        product.productName.toLowerCase().includes(searchTerm.toLowerCase()));

    const handleQuantityChange = (productId, value) => {
        setQuantity((prev) => ({ ...prev, [productId]: value }));
    };
    const handleNavigateToCart = () => {
        navigate(`/shoppingCart/${cartId}`);
    }

    const handleAddProductToCart = async (e, productId) => {
        e.preventDefault();
        try {
          const response = await handleAddProduct(productId, cartId, quantity[productId] || 1);
            if (response) {
                setAddedProducts((prev) => new Set(prev).add(productId));
            }
        } catch (err) {
            console.error('Error adding product to cart:', err);
            setError(err.message);
        }
    }
    const handleFetchProductListFromApi = async () => {
        setLoading(true);
        setError(null);
        try {
            const data = await fetchProductListFromApi();
            setProducts(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }
    const handleFetchProducts = async () => {
        setLoading(true);
        setError(null);
        try {
            const data = await fetchProducts();
            setProducts(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }
   useEffect(() => {
        handleFetchProducts();
       }, []);

    return {
        searchTerm,
        setSearchTerm,
        products,
        setProducts,
        loading,
        error,
        addedProducts,
        setAddedProducts,
        quantity,
        setQuantity,
        filteredProducts,
        handleQuantityChange,
        handleAddProductToCart,
        handleFetchProductListFromApi,
        handleFetchProducts,
        handleNavigateToCart
    };

}