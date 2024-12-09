import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

const recommendation = () => {
    const {cartId} = useParams();
    const [error, setError] = useState(null);
    const [recommendation, setRecommendation] = useState(null);
    useEffect(() => {
        const fetchRecommendation = async () => {
            console.log(cartId);
            try {
                const response = await fetch(`http://localhost:8000/recommendation/recommend/${cartId}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch recommendation.');
                }
                const data = await response.json();
                console.log(data);
                console.log(data.recommendation);
                setRecommendation(data);
            } catch (err) {
                console.log(err);
                setError("Failed to fetch recommendation.");
            }
        };
        if (cartId){
            fetchRecommendation();
        }
    }, [cartId]);
    if (error) return <p>{error}</p>;
    if (!recommendation) return <p>No recommendation found</p>;
    return (
        <div>
            <h4>Recommendation for cart {cartId}</h4>
            <ul>
                {recommendation.map((recommendation, index) => (
                    <li key={index}>{recommendation}</li>
                ))}
            </ul>

        </div>
    )
}
export default recommendation;