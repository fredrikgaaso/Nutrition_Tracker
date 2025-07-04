import {Box, Container, Typography} from "@mui/material";
import React, from "react";

const favorites = ({favoriteProducts}) => {

    return (
       <Container>
            <Box sx={{ marginTop: '20px', marginBottom: '20px' }}>
                <Typography variant="h4">Favorites</Typography>
                {favoriteProducts ? (
                    favoriteProducts.map((product, index) => (
                        <Box key={index} sx={{ marginBottom: '20px', border: '1px solid #ccc', borderRadius: '8px', padding: '16px' }}>
                            <Typography variant="h6">{product.productName} </Typography>
                            <Typography variant="body1">Nutrients:</Typography>
                            {product.nutritionalInfo.map((nutrient, nutrientIndex) => (
                                <Box key={nutrientIndex} sx={{ display: 'flex', justifyContent: 'space-between', marginBottom: '4px' }}>
                                    <Typography>{nutrient.nutrientName}</Typography>
                                    <Typography>{nutrient.nutrientValue}g</Typography>

                                </Box>
                            ) )}
                            <Typography variant="body1" sx={{ marginTop: '8px' }}>
                                Calories: {product.calories}g
                            </Typography>
                        </Box>
                    ))
                ) : (
                    <p>No favorite products found.</p>
                )}
            </Box>
       </Container>
    )
}
export default favorites;