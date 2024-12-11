import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

const SearchBar = () => {
    const { cartId } = useParams();
    const navigate = useNavigate();
    const [searchInput, setSearchInput] = useState("");
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [addedProducts, setAddedProducts] = useState(new Set());
    const [quantity, setQuantity] = useState({});

    useEffect(() => {
        const fetchProducts = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await fetch('http://localhost:8000/product/all');
                if (!response.ok) {
                    throw new Error('Failed to fetch products.');
                }
                const data = await response.json();
                setProducts(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchProducts();
    }, []);

    const handleChange = (e) => {
        setSearchInput(e.target.value);
    };

    const filteredProducts = products.filter((product) =>
        product.productName && product.productName.toLowerCase().includes(searchInput.toLowerCase())
    );

    const handleAddProductToCart = async (e, productId) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8000/cart/add`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    cartId,
                    productId,
                    quantity: quantity[productId] || 1
                })
            });
            if (response.ok) {
                setAddedProducts((prev) => new Set(prev).add(productId));
            } else {
                const errorData = await response.json();
                console.error('Error data:', errorData);
                throw new Error(`Failed to add product to cart: ${errorData.message}`);
            }
        } catch (err) {
            console.error('Error adding product to cart:', err);
        }
    };

    const handleGetProductList = async () => {
        try {
            const response = await fetch(`http://localhost:8000/product/fetch`);
            if (response.ok) {
                const data = await response.json();
                setProducts(data);
            } else {
                throw new Error('Failed to fetch products.');
            }
        } catch (err) {
            console.error('Error fetching products:', err);
    }
    };
    const handleNavigateToCart = () => {
        navigate(`/shoppingCart/${cartId}`);
    };

    const handleQuantityChange = (productId, value) => {
        setQuantity((prev) => ({ ...prev, [productId]: value }));
    };

    if (error) return <p>{error}</p>;
    if (loading) return <p>Loading...</p>;

    return (
        <div>
            <button onClick={handleNavigateToCart}>Back to cart</button>
            <button onClick={handleGetProductList}>Get product list</button>
            <input
                type="text"
                value={searchInput}
                onChange={handleChange}
                placeholder="Search..."
            />
            {filteredProducts.length === 0 ? (
                <p>No products found</p>
            ) : (
                <table>
                    <thead>
                    <tr>
                        <th>Food name</th>
                        <th>Calories</th>
                        <th>Nutritional Info</th>
                        <th>Add to Cart</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filteredProducts.map((product, index) => (
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
                            <td>
                                {addedProducts.has(product.id) ? (
                                    <form onSubmit={(e) => handleAddProductToCart(e, product.id)}>
                                        <input
                                            type="number"
                                            value={quantity[product.id] || 1}
                                            onChange={(e) => handleQuantityChange(product.id, e.target.value)}
                                            min="1"
                                        />
                                        <button type="submit">
                                            Confirm
                                        </button>
                                    </form>
                                ) : (
                                    <button onClick={() => setAddedProducts((prev) => new Set(prev).add(product.id))}>
                                        Add to Cart
                                    </button>
                                )}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default SearchBar;