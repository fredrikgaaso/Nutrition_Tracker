import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

const Recommendation = () => {
    const {cartId} = useParams();
    const [error, setError] = useState(null);
    const [recommendation, setRecommendation] = useState(null);
    const [allergens, setAllergens] = useState(null);
    const [desiredNutrition, setDesiredNutrition] = useState(null);

    useEffect(() => {
        const fetchRecommendation = async () => {
            console.log(cartId);
            try {
                const response = await fetch(`http://localhost:8000/recommendation/update/${cartId}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch recommendation.');
                }
                const data = await response.json();
                console.log(data.allergens);
                console.log(data.nutritionalValues);
                console.log(data.recommendedProducts);

                setRecommendation(data.recommendedProducts);
                setAllergens(data.allergens);
                setDesiredNutrition(data.nutritionalValues);
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
            <h5>Recommendation</h5>
            <ul>
                {recommendation && recommendation.map((recommendation, index) => (
                    <li key={index}>{recommendation}</li>
                ))}
            </ul>
            <h5>Allergens</h5>
            <ul>
                {allergens && allergens.map((allergen, index) => (
                    <li key={index}>{allergen}</li>
                ))}
            </ul>
            <h5>Desired Nutrition</h5>
            <ul>
                {desiredNutrition && desiredNutrition.map((nutrition, index) => (
                    <li key={index}>{nutrition}</li>
                ))}
            </ul>
        </div>
    );
};

export default Recommendation;