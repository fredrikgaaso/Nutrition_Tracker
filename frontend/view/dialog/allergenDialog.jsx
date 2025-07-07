import {Button, Checkbox, Dialog, DialogContent, DialogTitle, FormControlLabel} from "@mui/material";
import {useAllergenData} from "../../hooks/useAllergenData";
import React from "react";

const allergenDialog = ({open, onClose}) =>{

    const {
        selectedAllergens,
        allergens,
        handleAllergenChange,
        setAllergensCall,
        output
    } = useAllergenData();
    return (
        <Dialog open={open} onClose={onClose} PaperProps={{
            sx: {
                borderRadius: "16px",
                bgcolor: "background.paper",
                boxShadow: "0 8px 24px rgba(0,0,0,0.05)",
                width: "500px",
            },
        }}>

            <DialogTitle sx={{ fontSize: "1.5rem", fontWeight: 700 }}>
                Add Allergens
            </DialogTitle>
            <DialogContent>
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
                    onClick={() => {
                        setAllergensCall();
                        onClose();
                    }}
                    style={{ marginTop: '16px' }}
                >
                    Set Allergens
                </Button>

            </DialogContent>

        </Dialog>
    )

}
export default allergenDialog;