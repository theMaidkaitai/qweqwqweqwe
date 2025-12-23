import {$authHostUser} from "./index.ts";

export const getAllNotes = async () => {

    try {
        const {data} = await $authHostUser.get("/api/note/all");
        return data;
    }
    catch (e) {
        console.error("Неудалось получить карточки", e);
    }
}


export const getOneById = async (id: string | number) => {
    console.log("🔍 === getOneById вызван ===");
    console.log("ID получен:", id);
    console.log("Тип ID:", typeof id);

    try {
        const token = localStorage.getItem("token");
        console.log("Токен:", token ? "✅ Есть" : "❌ Нет");

        console.log(`📤 Отправляю GET запрос: /api/note/${id}`);

        const response = await $authHostUser.get(`/api/note/${id}`);

        console.log("✅ Запрос успешен!");
        console.log("Статус:", response.status);
        console.log("Данные ответа:", response.data);
        console.log("Тип данных:", typeof response.data);

        if (response.data) {
            console.log("ID в ответе:", response.data.id);
            console.log("Title в ответе:", response.data.title);
            console.log("Все поля:", Object.keys(response.data));
        } else {
            console.warn("⚠️ Данные пустые!");
        }

        return response.data;
    }
    catch (e: any) {
        console.error("❌ Ошибка в getOneById!");
        console.error("Сообщение:", e.message);

        if (e.response) {
            console.error("Статус ошибки:", e.response.status);
            console.error("Данные ошибки:", e.response.data);
            console.error("Заголовки ошибки:", e.response.headers);
        } else if (e.request) {
            console.error("Запрос был сделан, но нет ответа");
        }

        throw e; // Важно: пробрасываем ошибку дальше
    }
}

