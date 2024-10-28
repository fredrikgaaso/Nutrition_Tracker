import {BrowserRouter, Route, Routes} from "react-router-dom";
import SearchBar from './view/searchBar'
import ShoppingCart from "./view/shoppingCart";

function App() {
    return(
       <BrowserRouter>
           <Routes>
               <Route path={"searchbar"} element={<SearchBar/>}/>
               <Route path={"ShoppingCart"} element={<ShoppingCart/>}/>
           </Routes>
       </BrowserRouter>
    )
}
export default App;