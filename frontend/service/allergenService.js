export async function setAllergens(cartId, selectedAllergen) {
        console.log(`Setting allergens for cart ${cartId}:`, selectedAllergen);
        console.log(`Request body:`, JSON.stringify(selectedAllergen));
        const response = await fetch(`http://localhost:8000/cart/setAllergens/${cartId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(selectedAllergen),
        });

        if (!response.ok) {
            throw new Error('Failed to set allergens');
        }

        const responseData = await response.text();

        console.log(`Allergens set for cart ${cartId}:`, responseData);
        return responseData;
}