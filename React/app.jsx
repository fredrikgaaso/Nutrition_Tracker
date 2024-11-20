import {BrowserRouter, Route, Routes} from "react-router-dom";
import SearchBar from './view/searchBar'
import ShoppingCart from "./view/shoppingCart";
import FrontPage from "./view/frontPage"

function App() {
    return(
       <BrowserRouter>
           <Routes>
               <Route path="/searchbar/:cartId" element={<SearchBar/>}/>
               <Route path={"/shoppingCart/:cartId"} element={<ShoppingCart/>}/>
               <Route path="/frontpage" element={<FrontPage/>}/>
           </Routes>
       </BrowserRouter>
    )
}
export default App;