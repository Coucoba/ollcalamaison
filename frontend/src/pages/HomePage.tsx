import { Box, Typography } from "@mui/material";

export function HomePage() {
    return (
        <Box display="flex" flexDirection="column" justifyContent="center" alignItems="center" height="100%">
            <Typography variant="h5" textAlign="center" justifyContent="center">
                Bienvenue sur Ollcalamaison
            </Typography>
            <Typography variant="body1" textAlign="center" justifyContent="center">
                Ici gérer vos livreurs, vos tournées et vos livraison
            </Typography>
        </Box>
    )
}