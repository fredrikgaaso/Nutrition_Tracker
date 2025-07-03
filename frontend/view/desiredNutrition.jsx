import React from 'react';
import { useNutritionData } from "../hooks/useNutritionData";

const DesiredNutrition = () => {
    const {
        desiredProtein,
        desiredCarbs,
        desiredFat,
        handleDesiredProteinChange,
        handleDesiredCarbsChange,
        handleDesiredFatChange,
        fetchNutritionalValueCall,
        output,
    } = useNutritionData();

    return (
        <div>
            <h4>Desired Nutrition</h4>
            <p>Set your desired nutritional values:</p>
            <label>
                Protein (g):
                <input
                    type="number"
                    value={desiredProtein}
                    onChange={handleDesiredProteinChange}
                />
            </label>
            <br />
            <label>
                Carbs (g):
                <input
                    type="number"
                    value={desiredCarbs}
                    onChange={handleDesiredCarbsChange}
                />
            </label>
            <br />
            <label>
                Fat (g):
                <input
                    type="number"
                    value={desiredFat}
                    onChange={handleDesiredFatChange}
                />
            </label>
            <br />
            <button onClick={fetchNutritionalValueCall}>Set Desired Nutrition</button>
            <p>{output}</p>
        </div>
    );
};

export default DesiredNutrition;