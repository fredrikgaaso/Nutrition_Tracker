
import {setAllergens} from "../service/allergenService";
import {useState} from "react";
import {useParams} from "react-router-dom";

export const useAllergenData = () => {
    const [selectedAllergens, setSelectedAllergens] = useState([]);
    const allergens = ['gluten', 'lactose', 'nuts', 'soy', 'egg', 'fish', 'shellfish', 'vegan', 'sesame'];
    const [output, setOutput] = useState("");
    const { cartId } = useParams();


    const handleAllergenChange = (allergen) => {
        setSelectedAllergens((prevAllergens) =>
            prevAllergens.includes(allergen)
                ? prevAllergens.filter((a) => a !== allergen)
                : [...prevAllergens, allergen]
        );

    }
    const setAllergensCall = async () => {
     const data = await setAllergens(cartId, selectedAllergens);

     setOutput(data.message);
    }
    return {
        selectedAllergens,
        setSelectedAllergens,
        allergens,
        handleAllergenChange,
        setAllergensCall,
        output
    };
}