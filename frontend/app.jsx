import {BrowserRouter, Route, Routes} from "react-router-dom";
import SearchBar from './view/searchBar'
import ShoppingCart from "./view/shoppingCart";
import Login from "./view/login";

function App() {
    return(
       <BrowserRouter>
           <Routes>
               <Route path={"searchbar"} element={<SearchBar/>}/>
               <Route path={"ShoppingCart"} element={<ShoppingCart/>}/>
               <Route path={"login"} element={<Login/>}/>
           </Routes>
       </BrowserRouter>
    )
}
export default App;