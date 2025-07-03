import React from 'react';
import { useShoppingCartData } from "../hooks/useShoppingCartData";
import Recommendation from "./recommendation";
import Allergen from "./allergen";
import DesiredNutrition from "./desiredNutrition";
import { Button, Typography, Table, TableHead, TableRow, TableCell, TableBody, List, ListItem, Container } from '@mui/material';

const ShoppingCart = () => {
    const {
        shoppingCart,
        cartId,
        error,
        showRecommendation,
        showAllergens,
        showNutritionalValue,
        handleNavigateToProduct,
        handleNavigateToFrontpage,
        handleToggleRecommendation,
        handleToggleAllergens,
        handleToggleNutritionalValue,
        handleDeleteProductFromCart
    } = useShoppingCartData();

    if (error) return <Typography color="error">{error}</Typography>;
    if (!shoppingCart || !shoppingCart.productsList) return <Typography>No shopping cart found</Typography>;

    return (
        <Container>
            <Button variant="contained" color="primary" onClick={handleNavigateToFrontpage} style={{ marginBottom: '16px' }}>
                Go back to front page
            </Button>
            <Button variant="contained" color="primary" onClick={handleNavigateToProduct} style={{ marginBottom: '16px' }}>
                Go to SearchBar for Cart {cartId}
            </Button>
            <Button variant="contained" color="secondary" onClick={handleToggleAllergens} style={{ marginBottom: '16px' }}>
                {showAllergens ? 'Hide Allergens' : 'Add Allergens'}
            </Button>
            <Button variant="contained" color="secondary" onClick={handleToggleRecommendation} style={{ marginBottom: '16px' }}>
                {showRecommendation ? 'Hide Recommendations' : 'Get Recommendations'}
            </Button>
            <Button variant="contained" color="secondary" onClick={handleToggleNutritionalValue} style={{ marginBottom: '16px' }}>
                {showNutritionalValue ? 'Hide Nutritional Value' : 'Add nutritional value'}
            </Button>

            {showRecommendation && <Recommendation cartId={cartId} />}
            {showAllergens && <Allergen cartId={cartId} />}
            {showNutritionalValue && <DesiredNutrition cartId={cartId} />}

            <Typography variant="h4" gutterBottom>
                Product List:
            </Typography>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Product Name</TableCell>
                        <TableCell>Calories pr 100g</TableCell>
                        <TableCell>Nutritional Info pr 100g</TableCell>
                        <TableCell>Quantity</TableCell>
                        <TableCell>Actions</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {shoppingCart.productsList.map((product, index) => (
                        <TableRow key={index}>
                            <TableCell>{product.productName}</TableCell>
                            <TableCell>{product.calories}</TableCell>
                            <TableCell>
                                <List>
                                    {product.nutritionalInfo && Array.isArray(product.nutritionalInfo) ? (
                                        product.nutritionalInfo.map((nutrient, nutrientIndex) => (
                                            <ListItem key={nutrientIndex}>
                                                {nutrient.nutrientName}: {nutrient.nutrientValue}
                                            </ListItem>
                                        ))
                                    ) : (
                                        <ListItem>No nutritional info available</ListItem>
                                    )}
                                </List>
                            </TableCell>
                            <TableCell>{product.quantity}</TableCell>
                            <TableCell>
                                <Button
                                    variant="contained"
                                    color="error"
                                    onClick={() => handleDeleteProductFromCart(product.id)}
                                >
                                    Remove Product
                                </Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </Container>
    );
};

export default ShoppingCart;