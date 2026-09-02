import axios from "axios";

export const VITE_API_BASE_URL = "https://project-management-70hc.onrender.com";

const api = axios.create({
    baseURL: VITE_API_BASE_URL,
});

api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("jwt");

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        } else {
            delete config.headers.Authorization;
        }

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

api.defaults.headers.post["Content-Type"] = "application/json";

export default api;