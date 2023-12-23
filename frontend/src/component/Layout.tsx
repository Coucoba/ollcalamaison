import { Container, Grid } from "@mui/material";
import { Header } from "./Header";
import { SideBar } from "./SideBar";
import { Outlet } from "react-router-dom";

export function Layout(){
    return (
        <>
            <Header/>
            <Container>
                <Grid container spacing={2}>
                    <Grid item xs={12} sm={2} >
                        <SideBar/>
                    </Grid>
                    <Grid item xs={12} sm={10}>
                        <Outlet/>
                    </Grid>
                </Grid>
            </Container>
        </>
    )
}