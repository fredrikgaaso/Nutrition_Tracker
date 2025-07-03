import {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import { fetchRecommendations } from "../service/recommendationService";
export const useRecommendationData = () => {
    const [recommendations, setRecommendations] = useState([]);
    const [allergens, SetAllergens] = useState([]);
    const [desiredNutritions, setDesiredNutritions] = useState([]);
    const [error, setError] = useState(null);
    const {cartId} = useParams();

    const fetchRecommendationsCall = async () => {
        setError(null);
        try {
            const data = await fetchRecommendations(cartId);
            console.log('Fetched recommendations:', data);
            setRecommendations(data.recommendedProducts || []);
            SetAllergens(data.allergens || []);
            setDesiredNutritions(data.nutritionalValues || []);
        } catch (err) {
            console.error(err);
            setError("Failed to fetch recommendations.");
        }
    };

    useEffect(() => {
        if (cartId) {
            fetchRecommendationsCall();
        }

    }, [cartId]);


    return {
        recommendations,
        setRecommendations,
        error,
        setError,
        allergens,
        cartId,
        desiredNutritions,
        fetchRecommendationsCall
    };
}