import React from 'react';
import { useProductData } from '../hooks/useProductData';
import { TextField, Button, Table, TableHead, TableRow, TableCell, TableBody } from '@mui/material';

const ProductPage = () => {
    const {
        searchTerm,
        setSearchTerm,
        loading,
        error,
        addedProducts,
        setAddedProducts,
        quantity,
        filteredProducts,
        handleQuantityChange,
        handleFetchProductListFromApi,
        handleAddProductToCart,
        handleNavigateToCart,
    } = useProductData();

    if (error) return <p>{error}</p>;
    if (loading) return <p>Loading...</p>;

    return (
        <div style={{ padding: '20px' }}>
            <Button variant="contained" color="primary" onClick={handleNavigateToCart} style={{ marginRight: '10px' }}>
                Back to Cart
            </Button>
            <Button variant="contained" color="secondary" onClick={handleFetchProductListFromApi}>
                Get Product List
            </Button>
            <div style={{ margin: '20px 0' }}>
                <TextField
                    label="Search Products"
                    variant="outlined"
                    fullWidth
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
            </div>
            {filteredProducts.length === 0 ? (
                <p>No products found</p>
            ) : (
                <Table style={{ width: '650px', height: '400px', tableLayout: 'fixed' }}>                    <TableHead>
                        <TableRow>
                            <TableCell>Food Name</TableCell>
                            <TableCell>Calories</TableCell>
                            <TableCell>Nutritional Info</TableCell>
                            <TableCell>Add to Cart</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {filteredProducts.map((product) => (
                            <TableRow key={product.id}>
                                <TableCell>{product.productName}</TableCell>
                                <TableCell>{product.calories}</TableCell>
                                <TableCell>
                                    <ul style={{ paddingLeft: '20px' }}>
                                        {product.nutritionalInfo && Array.isArray(product.nutritionalInfo) ? (
                                            product.nutritionalInfo.map((nutrient, index) => (
                                                <li key={index}>
                                                    {nutrient.nutrientName}: {nutrient.nutrientValue}
                                                </li>
                                            ))
                                        ) : (
                                            <li>No nutritional info available</li>
                                        )}
                                    </ul>
                                </TableCell>
                                <TableCell>
                                    {addedProducts.has(product.id) ? (
                                        <div style={{ marginTop: '10px' }}>
                                            <TextField
                                                type="number"
                                                value={quantity[product.id] || 1}
                                                onChange={(e) => handleQuantityChange(product.id, e.target.value)}
                                                inputProps={{ min: 1 }}
                                                style={{ width: '80px', marginRight: '10px' }}
                                            />
                                            <Button
                                                variant="contained"
                                                color="primary"
                                                onClick={(e) => handleAddProductToCart(e, product.id)}
                                            >
                                                Confirm
                                            </Button>
                                        </div>
                                    ) : (
                                        <Button
                                            variant="contained"
                                            color="secondary"
                                            onClick={() => setAddedProducts((prev) => new Set(prev).add(product.id))}
                                        >
                                            Add to Cart
                                        </Button>
                                    )}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            )}
        </div>
    );
};

export default ProductPage;