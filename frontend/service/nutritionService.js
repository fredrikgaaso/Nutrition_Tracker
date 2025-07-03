export async function fetchNutritionalValue(cartId, desiredProtein, desiredCarbs, desiredFat) {
    console.log(`Setting desired nutritional values: Protein=${desiredProtein}, Carbs=${desiredCarbs}, Fat=${desiredFat}`);
    console.log(`Request body:`, JSON.stringify({
        desiredProtein: desiredProtein,
        desiredCarbs: desiredCarbs,
        desiredFat: desiredFat,
    }));
    const response = await fetch(`http://localhost:8000/cart/desiredNutrients/${cartId}`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            desiredProtein: desiredProtein,
            desiredCarbs: desiredCarbs,
            desiredFat: desiredFat,
        }),
    });
    if (!response.ok) {
        console.error('Failed to set desired nutrients:', response.statusText);
        return;
    }
    const data = await response.text();
    console.log('Nutritional value set:', data);
    return data;

}