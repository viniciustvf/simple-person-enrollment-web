import axios from "axios";
import { toast } from "react-toastify";

export const api = axios.create({
  baseURL: "http://localhost:8081",
});

api.interceptors.response.use(
  response => response,
  error => {
    const message =
      error.response?.data?.message ||
      "Erro inesperado. Tente novamente.";

      toast.error(message);
    return Promise.reject(error);
  }
);