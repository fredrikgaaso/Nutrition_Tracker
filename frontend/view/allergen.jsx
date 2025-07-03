import React from 'react';
import { useAllergenData } from "../hooks/useAllergenData";
import { Checkbox, FormControlLabel, Button, Typography } from '@mui/material';

const Allergen = () => {
    const {
        selectedAllergens,
        allergens,
        handleAllergenChange,
        setAllergensCall,
        output
    } = useAllergenData();

    return (
        <div>
            <Typography variant="h4" gutterBottom>
                Allergens
            </Typography>
            <Typography variant="body1" gutterBottom>
                Select your allergens:
            </Typography>
            {allergens.map((allergen) => (
                <FormControlLabel
                    key={allergen}
                    control={
                        <Checkbox
                            checked={selectedAllergens.includes(allergen)}
                            onChange={() => handleAllergenChange(allergen)}
                        />
                    }
                    label={allergen}
                />
            ))}
            <Button
                variant="contained"
                color="primary"
                onClick={setAllergensCall}
                style={{ marginTop: '16px' }}
            >
                Set Allergens
            </Button>
            {output && (
                <Typography variant="body2" color="textSecondary" style={{ marginTop: '16px' }}>
                    {output}
                </Typography>
            )}
        </div>
    );
};

export default Allergen;