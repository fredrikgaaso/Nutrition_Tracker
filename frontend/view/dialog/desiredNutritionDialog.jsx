import {
    Button,
    Checkbox,
    Dialog,
    DialogContent,
    DialogTitle,
    FormControlLabel,
    TextField,
    Typography
} from "@mui/material";
import React from "react";
import {useNutritionData} from "../../hooks/useNutritionData";

const desiredNutritionDialog= ({open, onClose}) => {
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
        <Dialog open={open} onClose={onClose} PaperProps={{
            sx: {
                borderRadius: "16px",
                bgcolor: "background.paper",
                boxShadow: "0 8px 24px rgba(0,0,0,0.05)",
                width: "500px",
            },
        }}>

            <DialogTitle sx={{ fontSize: "1.5rem", fontWeight: 700 }}>
                Desired Nutrition
            </DialogTitle>
            <DialogContent>
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
                    onClick={() => {
                        fetchNutritionalValueCall();
                        onClose();
                    }}
                    style={{ marginTop: '16px' }}
                >
                    Set Desired Nutrition
                </Button>

            </DialogContent>

        </Dialog>
    )
}
export default desiredNutritionDialog;