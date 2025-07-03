import React from 'react';
import { useRecommendationData } from "../hooks/useRecommendationData";
import { Typography, List, ListItem, Container } from '@mui/material';

const Recommendation = () => {
    const { recommendations, error, cartId, allergens, desiredNutritions } = useRecommendationData();

    if (error) return <Typography color="error">{error}</Typography>;
    if (!recommendations) return <Typography>No recommendation found</Typography>;

    return (
        <Container>
            <Typography variant="h4" gutterBottom>
                Recommendation for cart {cartId}
            </Typography>
            <Typography variant="h5" gutterBottom>
                Recommendation
            </Typography>
            <List>
                {recommendations.map((recommendation, index) => (
                    <ListItem key={index}>{recommendation}</ListItem>
                ))}
            </List>
            <Typography variant="h5" gutterBottom>
                Allergens
            </Typography>
            <List>
                {allergens.map((allergen, index) => (
                    <ListItem key={index}>{allergen}</ListItem>
                ))}
            </List>
            <Typography variant="h5" gutterBottom>
                Desired Nutrition
            </Typography>
            <List>
                {desiredNutritions.map((nutrition, index) => (
                    <ListItem key={index}>{nutrition}</ListItem>
                ))}
            </List>
        </Container>
    );
};

export default Recommendation;