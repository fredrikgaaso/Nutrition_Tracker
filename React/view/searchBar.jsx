import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

const SearchBar = () => {
    const { cartId } = useParams();
    const [searchInput, setSearchInput] = useState("");
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

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

    const handleAddProductToCart = async (productId) => {
        try {
            await fetch(`http://localhost:8000/cart/add/${cartId}/${productId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            alert('Product added to cart successfully');
        } catch (err) {
            alert('Failed to add product to cart');
        }
    };

    if (error) return <p>{error}</p>;
    if (loading) return <p>Loading...</p>;

    return (
        <div>
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
                                <button onClick={() => handleAddProductToCart(product.id)}>
                                    Add to Cart
                                </button>
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
