const gateway = import.meta.env.VITE_API_BASE_URL;

export async function fetchProducts(){
    const response = await fetch(`${gateway}/product/all`);
    if (!response.ok) {
        throw new Error('Failed to fetch products.');
    }
    const data = await response.json();
    return data;
}

export async function handleAddProduct(productId, cartId, quantity){
    const response = await fetch(`${gateway}/cart/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            productId: productId,
            cartId: cartId,
            quantity: quantity || 1
        })
    })
    if (!response.ok) {
        throw new Error(`Failed to add product to cart: ${errorData.message}`);
    }
    return response.ok;

}
export async function fetchProductListFromApi(){
    const response = await fetch(`${gateway}/product/fetch`);
    if (!response.ok) {
        throw new Error('Failed to fetch product list.');
    }
    const data = await response.json();
    return data;
}

export async function postFavoriteProduct(productId) {
    const response = await fetch(`${gateway}/product/setFavorite/${productId}`, {
        method: 'POST',
    });
    if (!response.ok) {
        throw new Error('Failed to add product to favorites.');
    }
    return response.ok;
}
export async function fetchFavoriteProducts() {
    const response = await fetch(`${gateway}/product/favorite`);
    if (!response.ok) {
        throw new Error('Failed to fetch favorite products.');
    }
    const data = await response.json();
    return data;
}