import React from 'react';
import { useShoppingCartData } from "../hooks/useShoppingCartData";
import Recommendation from "./recommendation";
import Allergen from "./allergen";
import DesiredNutrition from "./desiredNutrition";
import { Button, Typography, Table, TableHead, TableRow, TableCell, TableBody, List, ListItem, Container } from '@mui/material';
import { ButtonGrid } from "./layout/buttonGrid";

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
        handleDeleteProductFromCart,
        calculatedNutritionalValue,
        calculatedCalories
    } = useShoppingCartData();

    const buttons = [
        {
            label: 'Go back to front page',
            onClick: handleNavigateToFrontpage,
        },
        {
            label: 'Go to Product Page',
            onClick: handleNavigateToProduct,
        },
        {
            label: showAllergens ? 'Hide Allergens' : 'Add Allergens',
            onClick: handleToggleAllergens,
        },
        {
            label: showRecommendation ? 'Hide Recommendations' : 'Get Recommendations',
            onClick: handleToggleRecommendation,
        },
        {
            label: showNutritionalValue ? 'Hide Nutritional Value' : 'Add nutritional value',
            onClick: handleToggleNutritionalValue,
        }
        ];

    if (error) return <Typography color="error">{error}</Typography>;
    if (!shoppingCart || !shoppingCart.productsList) return <Typography>No shopping cart found</Typography>;

    return (
        <Container sx={{ marginTop: '20px', marginBottom: '20px' }}>
           <ButtonGrid buttons={buttons}/>

            {showRecommendation && <Recommendation cartId={cartId} />}
            {showAllergens && <Allergen cartId={cartId} />}
            {showNutritionalValue && <DesiredNutrition cartId={cartId} />}
            {shoppingCart.productsList.length > 0 && (
                <>
                    <Typography variant="h6">Nutritional value of cart:</Typography>
                    <Typography>Calories: {calculatedCalories} kcal</Typography>
                    {Object.entries(calculatedNutritionalValue)
                        .sort(([a], [b]) => a.localeCompare(b))
                        .map(([nutrientName, nutrientValue], index) => (
                            <Typography key={index}>
                                {nutrientName}: {nutrientValue}g
                            </Typography>
                        ))}
                </>
            )}

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
            {shoppingCart.productsList.length === 0 && (
                <Typography variant="h6" gutterBottom>
                    Your shopping cart is empty.
                </Typography>
            )}
        </Container>
    );
};

export default ShoppingCart;