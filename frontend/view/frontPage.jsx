import React, from 'react';
import { useFontPageData } from "../hooks/usefontPageData";

const FrontPage = () => {
    const { carts, error, handleRedirect, handleCreateNewCart } = useFontPageData();

    return (
        <div className="container">
            <h3>Existing Shopping Carts</h3>
            {error && <p>{error}</p>}
            <div>
                {carts.map(cart => (
                    <div key={cart.id} className="cart-item">
                        <button onClick={() => handleRedirect(cart.id)}>
                            Go to Shopping Cart {cart.id}
                        </button>
                    </div>
                ))}
            </div>
            <button onClick={handleCreateNewCart}>
                Create new cart
            </button>
        </div>
    );
};

export default FrontPage;