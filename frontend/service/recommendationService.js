const gateway = import.meta.env.VITE_API_BASE_URL;

export async function fetchRecommendations(cartId) {
    const response = await fetch(`${gateway}/recommendation/update/${cartId}`);
    if (!response.ok) {
        throw new Error('Failed to fetch recommendations');
    }
    const data = await response.json();
    console.log('Fetched recommendations:', data);
    return data;
}