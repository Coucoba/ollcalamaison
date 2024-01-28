
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Box, Button, Typography, Modal, FormControl, FormLabel, Input, FormHelperText, Switch } from "@mui/material";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { DeliveryDto } from "../api/type";
import { Controller, useForm } from "react-hook-form";
import { useFindDeliveries } from "../hooks/useDeliveries";

const columns: GridColDef[] = [
    { field: 'id', headerName: 'ID', minWidth: 90, flex:0.5},
    {
      field: 'pickupAddress',
      headerName: 'Adresse de ramassage',
      minWidth: 200,
      flex:2,
    },
    {
        field: 'depositAddress',
        headerName: 'Adresse de depose',
        minWidth: 200,
        flex:2,
    },
    {
        field: 'tour',
        headerName: "Tournee",
        minWidth: 200,
        flex:2,
        valueFormatter(params) {
            return params.value.name;
        },
    }
    
  ];

  const style = {
    position: 'absolute' as 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 800,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};

export function DeliveryPage() {
    
    const {data : result} = useFindDeliveries();

    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const { control, formState, handleSubmit } = useForm({defaultValues: { name: "", available: true }});
    const navigate = useNavigate();

    
    return (
        <Box display="flex" flexDirection="column" justifyContent="center" alignItems="center" height="100%">
            <Typography variant="body1" textAlign="center" justifyContent="center">
                Gestion des Livraisons
            </Typography>
            <Box sx={{ height: 400, width: '100%' }}>
                <DataGrid
                    rows={result ?? []}
                    columns={columns}
                    initialState={{
                    pagination: {
                        paginationModel: {
                        pageSize: 5,
                        },
                    },
                    }}
                    pageSizeOptions={[5]}
                    disableRowSelectionOnClick
                    getRowId={(row:DeliveryDto) => row.id}
                    onRowClick={(row) => console.log(result)}
                />
                <Button sx={{margin:3}} variant="contained" onClick={handleOpen}>Nouvelle Livraison</Button>
            </Box>

            <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
            <Typography align="center" variant="h5">Création d'une livraison</Typography>
            <form
                onSubmit={handleSubmit((e) => 
                    console.log(e)
                )}
            >
                <Box display="flex" flexDirection="column">
                <FormControl error={!!formState.errors.name}>
                    <FormLabel>Nom</FormLabel>
                    <Controller 
                        control={control} 
                        name="name" 
                        render={({field}) => (<Input placeholder="DeliveryPersonName" {...field}/>)}
                        rules={{
                            required: {
                                value: true,
                                message: "Champ obligatoire"
                            }
                        }}
                    />
                    <FormHelperText>{formState.errors.name?.message}</FormHelperText>
                </FormControl>
                <FormControl error={!!formState.errors.available}>
                    <FormLabel>Disponible</FormLabel>
                    <Controller 
                        control={control} 
                        name="available" 
                        render={({field}) => (<Switch checked={field.value}  {...field}/>)}
                    />
                </FormControl>
                <Button variant="contained" color="success" type="submit">Valider</Button>
            </Box>
            </form>
        </Box>
      </Modal>

    </Box>
    )
}