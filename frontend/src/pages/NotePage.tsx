import "../styles/Notes.css"
import NotePageComponent from "../components/NotePageComponent.tsx";
import {useContext, useEffect} from "react";
import {Context} from "../main.tsx";
import {getAllNotes} from "../http/notesApi.ts";
import {observer} from "mobx-react-lite";

const NotesPage = observer(() => {
    const { notes } = useContext(Context);

    useEffect(() => {
        getAllNotes().then(data => {
            console.log("Данные по карточкам:", data);
            notes.setNotes(data);
        });
    }, []);


    return (
        <div>
            <div className={"NotesPageWrapper"}>
                <div className={"NotesPage"}>
                    {notes._notes.map(note => (
                        <NotePageComponent  key = {note.id} note={note}/>
                    ))}
                </div>
            </div>
        </div>
    );
})

export default NotesPage;