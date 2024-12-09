import React, { useState } from 'react';
import { useParams } from "react-router-dom";

const Allergen = () => {
    const [selectedAllergen, setSelectedAllergen] = useState([]);
    const allergens = ['gluten', 'lactose', 'nuts', 'soy', 'egg', 'fish', 'shellfish', 'milk', 'wheat', 'sesame', 'celery'];
    const [output, setOutput] = useState("");
    const { cartId } = useParams();

    const handleAllergenChange = (allergen) => {
        setSelectedAllergen((prevAllergen) =>
            prevAllergen.includes(allergen)
                ? prevAllergen.filter((a) => a !== allergen)
                : [...prevAllergen, allergen]
        );
    };

    const fetchAllergens = async () => {
        const response = await fetch(`http://localhost:8000/cart/setAllergens/${cartId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(selectedAllergen),
        });

        if (!response.ok) {
            console.error('Failed to set allergens:', response.statusText);
            return;
        }

        const data = await response.text();
        setOutput(data.message);
    };

    return (
        <div>
            <h4>Allergens</h4>
            <p>Select your allergens:</p>
            {allergens.map((allergen) => (
                <label key={allergen}>
                    <input
                        type="checkbox"
                        checked={selectedAllergen.includes(allergen)}
                        onChange={() => handleAllergenChange(allergen)}
                    />
                    {allergen}
                </label>
            ))}
            <button onClick={fetchAllergens}>Set Allergens</button>
            <p>{output}</p>
        </div>
    );
};

export default Allergen;