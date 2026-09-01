import axios from "axios";

const LOCALHOST = "http://localhost:5054";

export const API_BASE_URL = LOCALHOST;

const api = axios.create({
  baseURL: API_BASE_URL,
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