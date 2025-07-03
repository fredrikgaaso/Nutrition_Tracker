import {BrowserRouter, Route, Routes} from "react-router-dom";
import ShoppingCart from "./view/shoppingCart";
import FrontPage from "./view/frontPage";
import Recommendation from "./view/recommendation";
import Allergen from "./view/allergen";
import DesiredNutrition from "./view/desiredNutrition";
import ProductPage from "./view/productPage";

function App() {
    return(
       <BrowserRouter>
           <Routes>
               <Route path="/product/:cartId" element={<ProductPage/>}/>
               <Route path={"/shoppingCart/:cartId"} element={<ShoppingCart/>}/>
               <Route path="/" element={<FrontPage/>}/>
               <Route path="/recommend/:cartId" element={<Recommendation/>}/>
               <Route path={"/allergens/:cartId"} element={<Allergen/>}/>
                <Route path={"/desiredNutrition/:cartId"} element={<DesiredNutrition/>}/>
           </Routes>
       </BrowserRouter>
    )
}
export default App;