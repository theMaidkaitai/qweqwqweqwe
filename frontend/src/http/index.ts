import axios from "axios";


const $host = axios.create({
    baseURL: "http://localhost:8080",
})

const $authHostUser = axios.create({
    baseURL: "http://localhost:8080",
})

const authInceptor = (config: { headers: { authorization: string; }; }) => {
    config.headers.authorization = `Bearer ${localStorage.getItem("token")}`
    return config
}

$authHostUser.interceptors.request.use(authInceptor)

export {
    $host,
    $authHostUser,
}