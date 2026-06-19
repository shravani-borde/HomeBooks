import api from "./axios";

export const getBooks = async (
  page = 0,
  size = 5
) => {
  const response = await api.get(
    `/books/paged?page=${page}&size=${size}`
  );

  return response.data;
};

export const searchBooks = async (
  keyword
) => {
  const response = await api.get(
    `/books/search?keyword=${keyword}`
  );

  return response.data;
};