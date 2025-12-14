import { AppBar, Box, Button, IconButton, Toolbar } from "@mui/material";
import { Link } from "react-router-dom";
import LogoSD from "../assets/SDLOGO.png";

export default function Menu() {
    return (
      <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static" sx={{ backgroundColor: "white" }}>
          <Toolbar>
            <IconButton sx={{ mr: 2 }}>
              <img width="50px" src={LogoSD} alt="Logo SD" />
            </IconButton>
                <Box sx={{ flexGrow: 1 }} />
                <Button sx={{color: "black"}} component={Link} to="/inscricao">Inscrição</Button>
                <Button sx={{color: "black"}} component={Link} to="/">Pessoa</Button>
          </Toolbar>
        </AppBar>
      </Box>
    );
  }