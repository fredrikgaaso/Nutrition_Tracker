import React from 'react';
import { useNutritionData } from "../hooks/useNutritionData";
import { TextField, Button, Typography } from '@mui/material';

const DesiredNutrition = () => {
    const {
        desiredProtein,
        desiredCarbs,
        desiredFat,
        handleDesiredProteinChange,
        handleDesiredCarbsChange,
        handleDesiredFatChange,
        fetchNutritionalValueCall,
        output,
    } = useNutritionData();

    return (
        <div>
            <Typography variant="h4" gutterBottom>
                Desired Nutrition
            </Typography>
            <Typography variant="body1" gutterBottom>
                Set your desired nutritional values:
            </Typography>
            <TextField
                label="Protein (g)"
                type="number"
                value={desiredProtein}
                onChange={handleDesiredProteinChange}
                fullWidth
                margin="normal"
            />
            <TextField
                label="Carbs (g)"
                type="number"
                value={desiredCarbs}
                onChange={handleDesiredCarbsChange}
                fullWidth
                margin="normal"
            />
            <TextField
                label="Fat (g)"
                type="number"
                value={desiredFat}
                onChange={handleDesiredFatChange}
                fullWidth
                margin="normal"
            />
            <Button
                variant="contained"
                color="primary"
                onClick={fetchNutritionalValueCall}
                style={{ marginTop: '16px' }}
            >
                Set Desired Nutrition
            </Button>
            {output && (
                <Typography variant="body2" color="textSecondary" style={{ marginTop: '16px' }}>
                    {output}
                </Typography>
            )}
        </div>
    );
};

export default DesiredNutrition;