
import { useState } from 'react';
import { useParams } from "react-router-dom";
import { fetchNutritionalValue } from "../service/nutritionService";
export const useNutritionData = () => {
    const [desiredProtein, setDesiredProtein] = useState(0);
    const [desiredCarbs, setDesiredCarbs] = useState(0);
    const [desiredFat, setDesiredFat] = useState(0);
    const [output, setOutput] = useState("");
    const { cartId } = useParams();

    const handleDesiredProteinChange = (e) => setDesiredProtein(parseInt(e.target.value));
    const handleDesiredCarbsChange = (e) => setDesiredCarbs(parseInt(e.target.value));
    const handleDesiredFatChange = (e) => setDesiredFat(parseInt(e.target.value));

    const fetchNutritionalValueCall = async () => {
        const data = await fetchNutritionalValue(cartId, desiredProtein, desiredCarbs, desiredFat);
        setOutput(data || "Nutritional values set successfully. Protein: " + desiredProtein + ", Carbs: " + desiredCarbs + ", Fat: " + desiredFat);
    };
    return {
        desiredProtein,
        setDesiredProtein,
        desiredCarbs,
        setDesiredCarbs,
        desiredFat,
        setDesiredFat,
        handleDesiredProteinChange,
        handleDesiredCarbsChange,
        handleDesiredFatChange,
        fetchNutritionalValueCall,
        output,
        cartId
    };


}