import icon from "../assets/icon.png"
import {useNavigate} from "react-router-dom";

// @ts-ignore
const NotePageComponent = ({note}) => {
    const navigate = useNavigate();

    function click () {
        navigate(`/notes/${note.id}`);
        console.log("Переход на заметку под номером:", note.id)
    }

    return (
        <div className="note-component-wrapper">
            <div className="note-component" style={{cursor: "pointer"}} onClick={click}>
                <div className="note-icon">
                    <img src={icon} alt="Иконка" />
                </div>

                <div className="note-divider"></div>

                <div className="note-content">
                    <h3>{note.title}</h3>
                    <p>Описание заметки</p>
                </div>
            </div>
        </div>
    );
};

export default NotePageComponent;