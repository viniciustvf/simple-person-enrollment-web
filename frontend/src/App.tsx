import { BrowserRouter, Route, Routes } from "react-router-dom";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import Pessoa from "./pages/Pessoa";
import MainLayout from "./layouts/MainLayout";
import Inscricao from "./pages/Inscricao";

export default function App() {
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <BrowserRouter>

        <ToastContainer position="top-right" autoClose={3000} theme="colored" />

        <Routes>
          <Route element={<MainLayout />}>
            <Route path="/" element={<Pessoa />} />
            <Route path="/inscricao" element={<Inscricao />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </LocalizationProvider>
  );
}
