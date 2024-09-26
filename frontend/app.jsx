import {BrowserRouter, Route, Routes} from "react-router-dom";
import Frontpage from '/view/frontpage'

function App() {
    return(
       <BrowserRouter>
           <Routes>
               <Route path={"frontpage"} element={<Frontpage/>}/>
           </Routes>
       </BrowserRouter>
    )
}
export default App;