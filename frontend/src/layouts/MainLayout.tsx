import { Box } from "@mui/material";
import { Outlet } from "react-router-dom";
import Menu from "../components/Menu";

export default function MainLayout() {
  return (
    <>
      <Menu />

      <Box
        sx={{
          width: "70%",
          margin: "0 auto",
          padding: 3,
          minHeight: "100vh",
        }}
      >
        <Outlet />
      </Box>
    </>
  );
}