export async function fetchProducts(){
    const response = await fetch('http://localhost:8000/product/all');
    if (!response.ok) {
        throw new Error('Failed to fetch products.');
    }
    const data = await response.json();
    return data;
}

export async function handleAddProduct(productId, cartId, quantity){
    const response = await fetch(`http://localhost:8000/cart/add`, {
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
    const response = await fetch('http://localhost:8000/product/fetch');
    if (!response.ok) {
        throw new Error('Failed to fetch product list.');
    }
    const data = await response.json();
    return data;
}