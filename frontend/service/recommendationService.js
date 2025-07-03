export async function fetchRecommendations(cartId) {
    const response = await fetch(`http://localhost:8000/recommendation/update/${cartId}`);
    if (!response.ok) {
        throw new Error('Failed to fetch recommendations');
    }
    const data = await response.json();
    console.log('Fetched recommendations:', data);
    return data;
}