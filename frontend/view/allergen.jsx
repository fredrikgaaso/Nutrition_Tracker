import {useAllergenData } from "../hooks/useAllergenData";

const Allergen = ( ) => {
 const { selectedAllergens,
     allergens,
     handleAllergenChange,
     setAllergensCall,
     output } = useAllergenData();

    return (
        <div>
            <h4>Allergens</h4>
            <p>Select your allergens:</p>
            {allergens.map((allergen) => (
                <label key={allergen}>
                    <input
                        type="checkbox"
                        checked={selectedAllergens.includes(allergen)}
                        onChange={() => handleAllergenChange(allergen)}
                    />
                    {allergen}
                </label>
            ))}
            <button onClick={setAllergensCall}>Set Allergens</button>
            <p>{output}</p>
        </div>
    );
};

export default Allergen;