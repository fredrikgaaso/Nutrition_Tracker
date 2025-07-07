import {useEffect, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import { fetchProducts, handleAddProduct, fetchProductListFromApi, postFavoriteProduct, fetchFavoriteProducts} from '../service/productService';

export const useProductData = () => {
    const { cartId } = useParams();
    const navigate = useNavigate();
    const [searchTerm, setSearchTerm] = useState("");
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [addedProducts, setAddedProducts] = useState(new Set());
    const [quantity, setQuantity] = useState({});
    const [currentPage, setCurrentPage] = useState(1);
    const [favoriteProducts, setFavoriteProducts] = useState([]);
    const productsPerPage = 10;
    const indexOfLastProduct = currentPage * productsPerPage;
    const indexOfFirstProduct = indexOfLastProduct - productsPerPage;

    const sanitizeSearchTerm = (term) => {
        return term.replace(/[^a-zA-Z0-9æøå\s]/g, '').trim();
    }
    const filteredProducts = products.filter((product) =>
        sanitizeSearchTerm(product.productName).toLowerCase().includes(searchTerm.toLowerCase()));

    const currentProducts = filteredProducts.slice(indexOfFirstProduct, indexOfLastProduct);

    const handlePageChange = (event, value) => {
        setCurrentPage(value);
    }
    const handleQuantityChange = (productId, value) => {
        setQuantity((prev) => ({ ...prev, [productId]: value }));
    };
    const handleNavigateToCart = () => {
        navigate(`/shoppingCart/${cartId}`);
    }

    const handleAddProductToCart = async (e, productId, cartId) => {
        e.preventDefault();
        console.log(`Adding product ${productId}, cart: ${cartId}, quantity: ${quantity[productId] || 1}` );
        try {
          const response = await handleAddProduct(productId, cartId, quantity[productId] || 1);
            if (response) {
                setAddedProducts((prev) => new Set(prev).add(productId));
                    setAddedProducts((prev) => {
                        const newSet = new Set(prev);
                        newSet.delete(productId);
                        return newSet;
                    });


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
    const handleFetchFavoriteProducts = async () => {
        setLoading(true);
        setError(null);
        try {
            const data = await fetchFavoriteProducts();
            setFavoriteProducts(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
        console.log(favoriteProducts);

    };
    const markFavoriteProduct = async (product) => {
        setFavoriteProducts((prev) => {
            const exists = prev.find((p) => p.id === product.id);
            if (exists) {
                return prev.filter((p) => p.id !== product.id);
            } else {
                return [...prev, product];
            }
        });
        await postFavoriteProduct(product.id);
    };

   useEffect(() => {
        handleFetchProducts();
        handleFetchFavoriteProducts();
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
        handleNavigateToCart,
        handlePageChange,
        productsPerPage,
        currentPage,
        currentProducts,
        favoriteProducts,
        markFavoriteProduct,
        fetchFavoriteProducts

    };

}