import React, { useState } from 'react';
import { useParams } from "react-router-dom";
import {parse} from "url";

const DesiredNutrition = () => {
    const [desiredProtein, setDesiredProtein] = useState(0);
    const [desiredCarbs, setDesiredCarbs] = useState(0);
    const [desiredFat, setDesiredFat] = useState(0);
    const [output, setOutput] = useState("");
    const { cartId } = useParams();

    const handleDesiredProteinChange = (e) => setDesiredProtein(parseInt(e.target.value));
    const handleDesiredCarbsChange = (e) => setDesiredCarbs(parseInt(e.target.value));
    const handleDesiredFatChange = (e) => setDesiredFat(parseInt(e.target.value));

    const fetchNutritionalValue = async () => {
        const response = await fetch(`http://localhost:8000/cart/desiredNutrients/${cartId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                desiredProtein: desiredProtein,
                desiredCarbs: desiredCarbs,
                desiredFat: desiredFat,
            }),
        });

        if (!response.ok) {
            console.log(desiredProtein, desiredCarbs, desiredFat);
            console.error('Failed to set desired nutrients:', response.statusText);
            return;
        }

        const data = await response.text();

        setOutput(data.message);
    };

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
            <button onClick={fetchNutritionalValue}>Set Desired Nutrition</button>
            <p>{output}</p>
        </div>
    );
};

export default DesiredNutrition;