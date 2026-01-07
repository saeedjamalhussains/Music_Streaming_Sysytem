"use client";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import SignUp from "./components/authentication/signup";
import Login from "./components/authentication/login";
import HarmonyLandingPage from "./Landercomponents/HarmonyLandingPage";
import AppLayout from "./AppLayout";
import HeroGeometric from "./Landercomponents/HeroGeometric";
import { AuthProvider } from "./components/authentication/AuthContext";
import ProtectedRoute from "./components/authentication/ProtectedRoute";
import AdminPanel from "./v0/AdminPanel";

import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export default function App() {
  return (
      <AuthProvider>
        <ToastContainer position="top-right" autoClose={3000} />
        <Router>
          <Routes>
            <Route path="/SignUp" element={<SignUp />} />
            <Route path="/Login" element={<Login />} />
            {/* <Route path="/" element={<HarmonyLandingPage />} /> */}
            <Route path="/" element={<HeroGeometric />} />
            <Route path="/app" element={<ProtectedRoute><AppLayout /></ProtectedRoute>} />
            <Route path="/admin" element={<ProtectedRoute><AdminPanel /></ProtectedRoute>} />
          </Routes>
        </Router>
      </AuthProvider>
  );
}
