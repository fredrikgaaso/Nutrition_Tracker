import {BrowserRouter, Route, Routes} from "react-router-dom";
import SearchBar from './view/searchBar'
import ShoppingCart from "./view/shoppingCart";
import FrontPage from "./view/frontPage";
import Recommendation from "./view/recommendation";
import Allergen from "./view/allergen";
import DesiredNutrition from "./view/desiredNutrition";

function App() {
    return(
       <BrowserRouter>
           <Routes>
               <Route path="/searchbar/:cartId" element={<SearchBar/>}/>
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