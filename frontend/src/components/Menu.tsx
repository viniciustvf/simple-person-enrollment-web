import { AppBar, Box, IconButton, Tab, Tabs, Toolbar } from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import LogoSD from "../assets/SDLOGO.png";

export default function Menu() {
  const location = useLocation();

  const currentTab = location.pathname.startsWith("/inscricao")
    ? "/inscricao"
    : "/";

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static" sx={{ backgroundColor: "white" }} elevation={1}>
        <Toolbar sx={{ height: 72, minHeight: 72, px: 2 }}>
          <IconButton sx={{ mr: 2 }}>
            <img width="50px" src={LogoSD} alt="Logo SD" />
          </IconButton>

          <Box sx={{ flexGrow: 1 }} />

          <Box
            sx={{
              mr: 6,
              height: "100%",
              display: "flex",
              alignItems: "stretch",
            }}
          >
            <Tabs
              value={currentTab}
              textColor="primary"
              indicatorColor="primary"
              sx={{
                height: "100%",
                "& .MuiTabs-flexContainer": { height: "100%" },
                "& .MuiTab-root": {
                  height: "100%",
                  maxHeight: "100%",
                  minHeight: "100%",
                  textTransform: "none",
                  fontWeight: 700,
                  fontSize: "1.05rem",
                  px: 3,
                },
              }}
            >
              <Tab label="Pessoa" value="/" component={Link} to="/" />
              <Tab
                label="Inscrição"
                value="/inscricao"
                component={Link}
                to="/inscricao"
              />
            </Tabs>
          </Box>
        </Toolbar>
      </AppBar>
    </Box>
  );
}