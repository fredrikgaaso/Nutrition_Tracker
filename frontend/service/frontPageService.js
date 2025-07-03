export async function fetchCarts () {
    const response = await fetch('http://localhost:8000/cart/all');
    if (!response.ok) {
        throw new Error('Failed to fetch shopping carts');
    }
    const data = await response.json();
    console.log('Fetched shopping carts:', data);
    return data;
}
export async function createNewCart () {
    const response = await fetch('http://localhost:8000/cart/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (!response.ok) {
        throw new Error('Failed to create a new cart');
    }

    const newCart = await response.json();
    console.log('Created new shopping cart:', newCart);
    return newCart;
}