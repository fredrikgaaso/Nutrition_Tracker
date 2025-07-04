import React from 'react';
import { useProductData } from '../hooks/useProductData';
import {
    TextField,
    Button,
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    Container,
    Pagination, IconButton
} from '@mui/material';
import StarBorderRoundedIcon from '@mui/icons-material/StarBorderRounded';
import { ButtonGrid } from './layout/buttonGrid';

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
        handlePageChange,
        productsPerPage,
        currentPage,
        currentProducts
    } = useProductData();

    const buttons = [
        {
            label: 'Back to Cart',
            onClick: handleNavigateToCart,
        },
        {
            label: 'Get Product List',
            onClick: handleFetchProductListFromApi,

        }
    ]

    if (error) return <p>{error}</p>;
    if (loading) return <p>Loading...</p>;

    return (
        <Container sx={{ marginTop: '20px', marginBottom: '20px' }}>
            <ButtonGrid buttons={buttons} />
            <Container sx={{ marginTop: '20px', marginBottom: '20px' }}>
                <TextField
                    label="Search Products"
                    variant="outlined"
                    fullWidth
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
            </Container>
            {filteredProducts.length === 0 ? (
                <p>No products found</p>
            ) : (
                <>
                    <Table style={{ width: '650px', height: '400px', tableLayout: 'fixed' }}>
                        <TableHead>
                            <TableRow>
                                <TableCell>Food Name</TableCell>
                                <TableCell>Calories</TableCell>
                                <TableCell>Nutritional Info</TableCell>
                                <TableCell>Add to Cart</TableCell>
                                <TableCell></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {currentProducts.map((product) => (
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
                                                    color="secondary"
                                                    onClick={(e) => handleAddProductToCart(e, product.id)}
                                                >
                                                    Confirm
                                                </Button>
                                            </div>
                                        ) : (
                                            <Button
                                                variant="contained"
                                                color="primary"
                                                onClick={() => setAddedProducts((prev) => new Set(prev).add(product.id))}
                                            >
                                                Add to Cart
                                            </Button>
                                        )}
                                    </TableCell>
                                    <TableCell><IconButton><StarBorderRoundedIcon/></IconButton></TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                    <Pagination
                        count={Math.ceil(filteredProducts.length / productsPerPage)}
                        page={currentPage}
                        onChange={handlePageChange}
                        sx={{ marginTop: '20px', display: 'flex', justifyContent: 'center' }}
                    />
                </>
            )}
        </Container>
    );
};

export default ProductPage;