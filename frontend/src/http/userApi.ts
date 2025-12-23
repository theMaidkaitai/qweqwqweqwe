import {$host} from "./index.ts";
import {jwtDecode} from "jwt-decode";

export const registration = async (nick: string, email: string, password: string) => {
    const {data} = await $host.post("/api/auth/register", {nick, password, email});

    // УБРАТЬ JSON.stringify - токен это строка!
    localStorage.setItem("token", data.token);
    console.log("Токен из регистрации", data.token);
    return jwtDecode(data.token);
}

export const check = async () => {
    const token = localStorage.getItem("token");
    if (!token) {
        throw new Error("No token found")
    }

    let actualToken = token;
    try {
        actualToken = JSON.parse(token);
    } catch (e) {
        actualToken = token;
    }

    const decoded = jwtDecode(actualToken);



    console.log("Токен чек:", actualToken);
    return decoded;
}

export const login = async (nick: string, email: string, password: string) => {
    const {data} = await $host.post("api/auth/login", {nick ,email, password});
    const token = data.token || data;



    localStorage.setItem("token", token);
    console.log("Токен из login", token);
    return jwtDecode(token);
}

export const getAllInfo = async () => {
    const token = localStorage.getItem("token");
    if (!token) {
        throw new Error("No token found")
    }
    const decoded = jwtDecode(token);

    
    return decoded;
}