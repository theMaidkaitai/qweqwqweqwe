import  {useEffect, useState} from 'react';
import {getOneById} from "../http/notesApi.ts";
import {useParams} from "react-router-dom";

const NotePages = () => {


    const [note, setNote] = useState(null)

    const { id } = useParams<{ id: string }>();



    useEffect(() => {
        console.log("  === NotePages компонент ===");
        console.log("ID из URL:", id);

        if (!id) {
            console.error("❌ ID не найден в URL");
            return;
        }

        console.log("🔄 Начинаю загрузку заметки...");

        getOneById(id)
            .then(data => {
                console.log("Данные получены в компоненте:", data);

                if (!data) {
                    console.error("❌ Данные пустые!");
                    setNote(null);
                } else {
                    console.log("✅ Устанавливаю заметку в состояние");
                    setNote(data);
                }

            })
            .catch(error => {
                console.error("Ошибка в компоненте:", error);
            });
    }, [id]);

    if (!note) {
        return (
            <div style={{padding: '20px'}}>
                <h3>Заметка не найдена</h3>
                <p>Заметка с ID {id} не существует</p>
                <button onClick={() => window.history.back()}>
                    Назад к списку
                </button>
            </div>
        );
    }
    return (
        <div>
            {note.title}
        </div>
    );
};

export default NotePages;