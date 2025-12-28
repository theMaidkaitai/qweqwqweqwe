import axios from "axios";


const $host = axios.create({
    baseURL: "http://87.228.88.252",
})

const $authHostUser = axios.create({
    baseURL: "http://87.228.88.252",
})

const authInceptor = (config: { headers: { authorization: string; }; }) => {
    config.headers.authorization = `Bearer ${localStorage.getItem("token")}`
    return config
}

// @ts-ignore
$authHostUser.interceptors.request.use(authInceptor)

export {
    $host,
    $authHostUser,
}