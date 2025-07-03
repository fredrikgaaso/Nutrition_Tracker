export async function fetchShoppingCarts(cartId) {
    const response = await fetch(`http://localhost:8000/cart/${cartId}`);
    if (!response.ok) {
        throw new Error('Failed to fetch shopping carts.');
    }
    const data = await response.json();
    return data;
}
export async function deleteProductFromCart(cartId, productId) {
    const response = await fetch(`http://localhost:8000/cart/remove`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            cartId,
            productId,
        }),
    });
    if (!response.ok) {
        throw new Error('Failed to delete product from cart.');
    }
    const text = await response.text();
    return text ? JSON.parse(text) : null;
}
