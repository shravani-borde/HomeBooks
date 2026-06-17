import api from "../api/axiosConfig";

export const loginUser = async (loginData) => {

    const response = await api.post(
        "/auth/login",
        loginData
    );

    return response.data;
};