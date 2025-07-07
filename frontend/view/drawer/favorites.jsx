import {Box, Container, Typography, IconButton} from "@mui/material";
import React from "react";
import DeleteRounded from '@mui/icons-material/DeleteRounded';
import AddCircleOutlineRoundedIcon from '@mui/icons-material/AddCircleOutlineRounded';

const favorites = ({favoriteProducts, handleRemoveFavoriteProduct, handleAddProductToCartFromFavorite, addButton, cartId}) => {
    return (
       <Container>
            <Box sx={{ marginTop: '20px', marginBottom: '20px' }}>
                <Typography variant="h4">Favorites</Typography>
                {favoriteProducts.length>0 ? (
                    favoriteProducts.map((product, index) => (
                        <Box key={index} sx={{ marginBottom: '20px', border: '1px solid #ccc', borderRadius: '8px', padding: '16px', paddingTop: '40px', paddingBottom: '40px',   position: 'relative',
                        }}>
                            <IconButton
                                onClick={() => handleRemoveFavoriteProduct(product)}
                                sx={{
                                    color: "error.main",
                                    position: "absolute",
                                    top: 8,
                                    right: 8,
                                }}
                            >
                                <DeleteRounded />
                            </IconButton>
                            {addButton === true ? (
                                <IconButton
                                onClick={(e)=> handleAddProductToCartFromFavorite(e, cartId, product.id)}
                                sx={{
                                    position: "absolute",
                                    top: 8,
                                    left: 8,
                                }}
                                >
                                    <AddCircleOutlineRoundedIcon/>
                                </IconButton>
                            ) : null}
                            <Typography variant="h6">{product.productName} </Typography>
                            <Typography variant="body1">Nutrients:</Typography>
                            {product.nutritionalInfo.map((nutrient, nutrientIndex) => (
                                <Box key={nutrientIndex} sx={{ display: 'flex', justifyContent: 'space-between', marginBottom: '4px' }}>
                                    <Typography>{nutrient.nutrientName}</Typography>
                                    <Typography>{nutrient.nutrientValue}g</Typography>

                                </Box>
                            ) )}
                            <Typography variant="body1" sx={{ marginTop: '8px' }}>
                                Calories: {product.calories}kcal
                            </Typography>
                        </Box>
                    ))
                ) : (
                    <Typography variant="h6">No favorite products found.</Typography>
                )}
            </Box>
       </Container>
    )
}
export default favorites;