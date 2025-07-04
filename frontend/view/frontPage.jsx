import React from 'react';
import { useFontPageData } from "../hooks/usefontPageData";
import { Button, Typography, Container, Card, CardContent } from '@mui/material';

const FrontPage = () => {
    const { carts, error, handleRedirect, handleCreateNewCart } = useFontPageData();

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
                                Go to Shopping Cart {cart.id}
                            </Button>
                        </CardContent>
                    </Card>
                ))}
            </div>
            <Button
                variant="contained"
                color="primary"
                onClick={handleCreateNewCart}
                style={{ marginTop: '16px' }}
            >
                Create new cart
            </Button>
        </Container>
    );
};

export default FrontPage;