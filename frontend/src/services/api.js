import axios from 'axios';

const API_URL = process.env.BASE_API_URL || import.meta.env.VITE_BASE_API_URL;

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

export const userService = {
  register: async (username, email, password) => {
    try {
      const response = await api.post('/user/add', {
        username,
        email,
        password
      });
      return response.data;
    } catch (error) {
      throw error.response?.data || 'Registration failed';
    }
  },

  login: async (email, password) => {
    try {
      const response = await api.post('/user/validate', {
        email,
        password
      });
      if (response.data.jwtToken) {
        localStorage.setItem('user', JSON.stringify(response.data));
      }
      return response.data;
    } catch (error) {
      throw error.response?.data || 'Login failed';
    }
  },

  logout: () => {
    localStorage.removeItem('user');
  }
};