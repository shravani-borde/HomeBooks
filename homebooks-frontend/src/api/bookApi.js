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

export const addToTbr = async (
  id
) => {
  const response =
    await api.post(
      `/user/tbr/${id}`
    );

  return response.data;
};

export const likeBook = async (
  id
) => {
  const response =
    await api.post(
      `/user/liked/${id}`
    );

  return response.data;
};

export const getTbrBooks =
  async () => {
    const response =
      await api.get("/user/tbr");

    return response.data;
  };

export const getLikedBooks =
  async () => {
    const response =
      await api.get("/user/liked");

    return response.data;
  };

export const rateBook = async (
  bookId,
  score
) => {
  const response =
    await api.post(
      `/ratings/${bookId}`,
      {
        score: score
      }
    );

  return response.data;
};

export const getAverageRating =
  async (bookId) => {
    const response =
      await api.get(
        `/ratings/book/${bookId}/average`
      );

    return response.data;
  };

export const getTopRatedBooks =
  async () => {
    const response =
      await api.get(
        "/ratings/top-rated"
      );

    return response.data;
  };


export const getMyRatings =
  async () => {
    const response =
      await api.get(
        "/ratings/my"
      );

    return response.data;
  };