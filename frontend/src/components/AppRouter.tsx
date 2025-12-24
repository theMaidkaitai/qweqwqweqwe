import {Routes, Route} from "react-router-dom";
import RegPage from "../pages/RegPage.tsx";
import AuthPage from "../pages/AuthPage.tsx";
import NotePage from "../pages/NotePage.tsx";
import NotePages from "../pages/NotePages.tsx";

const AppRouter = () => {



    return (
        <Routes>
            <Route path="/notes" element={<NotePage/>}/>
            <Route path="/notes/:id" element={<NotePages/>}/>
            <Route path="/auth/reg" element={<RegPage/>}/>
            <Route path="/auth/login" element={<AuthPage/>}/>

        </Routes>
    );
};

export default AppRouter;