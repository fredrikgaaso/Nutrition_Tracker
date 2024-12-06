import {useEffect, useState} from "react";

const recommendation = () => {
    const [cartId, setCartId] = useState("");
    const [error, setError] = useState(null);




    useEffect(() => {
        const fetchRecommendation = async () => {
            try {
                const response = await fetch(`http://localhost:8000/recommendation/recommend/${cartId}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch recommendation.');
                }
                const data = await response.json();
                console.log(data);
            } catch (err) {
                setError("Failed to fetch recommendation.");
            }
        };
        fetchRecommendation();
    }, []);

    return (
        <div>
            <h2>Recommendation</h2>
            <p>Recommendation for cart {cartId}</p>
            <p>{error}</p>
            
        </div>
    )
}