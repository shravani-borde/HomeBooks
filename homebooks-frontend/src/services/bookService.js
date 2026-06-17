import api from "../api/axiosConfig";

export const getAllBooks = async () => {
    const response = await api.get("/books");
    return response.data;
};