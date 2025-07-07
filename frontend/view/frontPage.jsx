import React from 'react';
import { useFontPageData } from "../hooks/usefontPageData";
import {Button, Typography, Container, Card, CardContent, IconButton} from '@mui/material';
import NewCartDialog from "./dialog/newCartDialog";
import AddCircleOutlineRoundedIcon from "@mui/icons-material/AddCircleOutlineRounded";
import DeleteRounded from "@mui/icons-material/DeleteRounded";

const FrontPage = () => {
    const { carts, error, handleRedirect, handleCreateNewCart, handleDeleteCart } = useFontPageData();
    const [openNewCartDialog, setOpenNewCartDialog] = React.useState(false);
    const [isDrawerOpen, setIsDrawerOpen] = React.useState(false);


    const handleOpenNewCartDialog = () => {
        setIsDrawerOpen(true);
        setOpenNewCartDialog(true);
    }
    const handleCloseNewCartDialog = () => {
        setIsDrawerOpen(false);
        setOpenNewCartDialog(false);
    }


    return (
        <Container>
            <Typography variant="h4" gutterBottom>
                Existing Shopping Carts
            </Typography>
            {error && (
                <Typography variant="body1" color="error">
                    {error}
                </Typography>
            )}
            <div>
                {carts.map(cart => (
                    <Card key={cart.id} style={{ marginBottom: '16px' }}>
                        <CardContent>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={() => handleRedirect(cart.id)}
                            >
                                Go to Shopping Cart {cart.cartName}
                            </Button>
                            <IconButton
                                sx={{
                                    color: "error.main",

                                }}
                                onClick={()=> handleDeleteCart(cart.id)}
                            >
                                <DeleteRounded />
                            </IconButton>
                    </CardContent>
                    </Card>
                ))}
            </div>
            <Button
                variant="contained"
                color="primary"
                onClick={handleOpenNewCartDialog}
                style={{ marginTop: '16px' }}
            >
                Create new cart
            </Button>
            <NewCartDialog handleCreateNewCart={handleCreateNewCart} onClose={handleCloseNewCartDialog} open={openNewCartDialog} handleRedirect={handleRedirect}/>
        </Container>
    );
};

export default FrontPage;