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
    Pagination, IconButton, Drawer, Box
} from '@mui/material';
import StarBorderRoundedIcon from '@mui/icons-material/StarBorderRounded';
import StarRateRoundedIcon from '@mui/icons-material/StarRateRounded';
import { ButtonGrid } from './layout/buttonGrid';
import Favorites from "./drawer/favorites";
import {TopBar} from "./layout/topBar";

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
        currentProducts,
        favoriteProducts,
        markFavoriteProduct,
    } = useProductData();

    const [open, setOpen] = React.useState(false);
    const [isDrawerOpen, setIsDrawerOpen] = React.useState(false);
    const title = "Products";
    const subtitle = "Browse all available products";
    const handleRemoveFavoriteProduct = (product) => {
        markFavoriteProduct(product);
    }

    const handleOpenAndClose = () => {
        setOpen(!open);
        setIsDrawerOpen(!isDrawerOpen);
    };

    const handleClose = () => {
        setOpen(false);
        setIsDrawerOpen(false);
    };

    const buttons = [
        {
            label: 'Get Product List',
            onClick: handleFetchProductListFromApi,

        },
        {
            label: 'Favorites',
            onClick: handleOpenAndClose,
        }
    ]
    const topBarButtons = [
        {
            label: 'Back to Cart',
            onClick: handleNavigateToCart,
        },
    ]

    if (error) return <p>{error}</p>;
    if (loading) return <p>Loading...</p>;

    return (
        <Container sx={{ marginTop: '20px', marginBottom: '20px' }}>
            <Box sx={{marginBottom: '80px'}}><TopBar title={title} subtitle={subtitle} topBarButtons={topBarButtons} /></Box>

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
                                        {product.nutritionalInfo && Array.isArray(product.nutritionalInfo) ? (
                                            product.nutritionalInfo.map((nutrient, index) => (
                                                <Box key={index}>
                                                    {nutrient.nutrientName}: {nutrient.nutrientValue}
                                                </Box>
                                            ))
                                        ) : (
                                            <Box>No nutritional info available</Box>
                                        )}
                                    </TableCell>
                                    <TableCell>
                                        {addedProducts.has(product.id) ? (
                                            <Box style={{ marginTop: '10px' }}>
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
                                            </Box>
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
                                    <TableCell>
                                        <IconButton onClick={() => markFavoriteProduct(product)}>
                                            {favoriteProducts.some((p) => p.id === product.id) ? (
                                                <StarRateRoundedIcon sx={{ color: '#FFD600' }} />
                                            ) : (
                                                <StarBorderRoundedIcon />
                                            )}
                                        </IconButton>
                                    </TableCell>

                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                    <Drawer
                        sx={{
                            width: 300,
                            flexShrink: 0,
                            '& .MuiDrawer-paper': {
                                width: 300,
                                boxSizing: 'border-box',
                            },
                        }}
                        anchor={'right'}
                        open={isDrawerOpen}
                        onClose={handleClose}>

                        <Favorites
                        favoriteProducts={favoriteProducts}
                        onRemoveFavorite={handleRemoveFavoriteProduct}
                        />
                    </Drawer>

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