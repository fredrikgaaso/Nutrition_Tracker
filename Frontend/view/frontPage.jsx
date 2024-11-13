import ShoppingCart from "./shoppingCart";

const FrontPage = ()=> {
    return (
        <div>
            <button><ShoppingCart userId={1} /></button>
            <button><ShoppingCart userId={2} /></button>
        </div>
    );
}
export default FrontPage;