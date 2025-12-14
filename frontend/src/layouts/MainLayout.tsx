import { Box } from "@mui/material";
import Menu from "../components/Menu";
import { Outlet } from "react-router-dom";

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
  