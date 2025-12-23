import './styles/App.css'
import {BrowserRouter} from "react-router-dom";
import NavBar from "./components/Navbar.tsx";
import AppRouter from "./components/AppRouter.tsx";
import {useContext, useEffect} from "react";
import {Context} from "./main.tsx";
import {observer} from "mobx-react-lite";
import {check} from "./http/userApi.ts";

const App = observer(() => {

    const { user } = useContext(Context);

    useEffect(() => {
        check().then(data => {
            user.setUser(data);
            user.setIsAuth(true);
        }).catch(error => {
            console.log("User not authenticated:", error);
            user.setIsAuth(false);
            user.setUser({});
        });
    }, []);

    return (
        <BrowserRouter>
            <NavBar/>
            <AppRouter/>
        </BrowserRouter>
    )
})


export default App