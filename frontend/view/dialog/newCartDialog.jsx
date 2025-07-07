import React, { useState } from "react";
import { Dialog, DialogContent, DialogTitle, TextField, Button, Box } from "@mui/material";

const NewCartDialog = ({ open, onClose, handleCreateNewCart, handleRedirect }) => {
    const [cartName, setCartName] = useState("");

    const handleCreate = () => {
        handleCreateNewCart(cartName);
        onClose();
    };

    const handleCancel = () => {
        onClose();
    };

    return (
        <Dialog open={open} onClose={onClose} PaperProps={{ sx: { borderRadius: 2, minWidth: 350 } }}>
            <DialogTitle>Create new shopping cart</DialogTitle>
            <DialogContent>
                <TextField
                    label="Cart Name"
                    value={cartName}
                    onChange={e => setCartName(e.target.value)}
                    fullWidth
                    margin="normal"
                    autoFocus
                />
                <Box sx={{ display: "flex", justifyContent: "flex-end", gap: 1, mt: 2 }}>
                    <Button variant="contained" color="primary" onClick={handleCreate} disabled={!cartName}>
                        Create
                    </Button>
                    <Button variant="outlined" color="secondary" onClick={handleCancel}>
                        Cancel
                    </Button>
                </Box>
            </DialogContent>
        </Dialog>
    );
};

export default NewCartDialog;