import { Box } from "@mui/material";
import { Outlet } from "react-router-dom";
import Menu from "../components/Menu";

export default function MainLayout() {
  return (
    <>
      <Menu />

      <Box
        sx={{
          minHeight: "100vh",
          background:
            "linear-gradient(135deg, #ffffff 0%, #d9ecff 50%, #ffffff 100%)",
          backgroundSize: "150% 150%",
          animation: "bgMove 3s ease-in-out infinite",
          "@keyframes bgMove": {
            "0%": { backgroundPosition: "0% 50%" },
            "50%": { backgroundPosition: "100% 50%" },
            "100%": { backgroundPosition: "0% 50%" },
          },
        }}
      >
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
      </Box>
    </>
  );
}