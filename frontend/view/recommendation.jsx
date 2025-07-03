import {useRecommendationData} from "../hooks/useRecommendationData";

const Recommendation = () => {
    const {  recommendations,
        error,
        cartId,
        allergens,
        desiredNutritions,} = useRecommendationData();

    if (error) return <p>{error}</p>;
    if (!recommendations) return <p>No recommendation found</p>;

    return (
        <div>
            <h4>Recommendation for cart {cartId}</h4>
            <h5>Recommendation</h5>
            <ul>
                {recommendations && recommendations.map((recommendation, index) => (
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
                {desiredNutritions && desiredNutritions.map((nutrition, index) => (
                    <li key={index}>{nutrition}</li>
                ))}
            </ul>
        </div>
    );
};

export default Recommendation;