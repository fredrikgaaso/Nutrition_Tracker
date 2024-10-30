import React, { useState, useEffect } from 'react';
import axios from 'axios';

const SearchBar = () => {
    const [searchInput, setSearchInput] = useState("");
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await axios.get('http://localhost:8080/products/all');
                setProducts(response.data);
                setLoading(false);
            } catch (err) {
                setError("Failed to fetch data from the microservice.");
                setLoading(false);
            }
        };

        fetchProducts();
    }, []);

    const handleChange = (e) => {
        setSearchInput(e.target.value);
    };

    const filteredProducts = products.filter((product) =>
        product.productName.toLowerCase().includes(searchInput.toLowerCase())
    );

    if (loading) return <p>loading...</p>;

    if (error) return <p>{error}</p>

    if (searchInput.length === 0) return <div>
        <input
            type="text"
            value={searchInput}
            onChange={handleChange}
            placeholder="Search..."
        /> <p>no products found</p>
    </div>

    if (!loading && !error && searchInput.length>0) return (
        <div>
            <input
                type="text"
                value={searchInput}
                onChange={handleChange}
                placeholder="Search..."
            />
                <table>
                    <thead>
                    <tr>
                        <th>Food name</th>
                        <th>Nutritional Info</th>
                        <th>Calories</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filteredProducts.map((product, index) => (
                        <tr key={index}>
                            <td>{product.productName}</td>
                            <td>{product.calories}</td>
                            <td>
                                <ul>
                                    {product.nutritionalInfo.map((nutrient, nutrientIndex) => (
                                        <li key={nutrientIndex}>
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
    )
};

export default SearchBar;
