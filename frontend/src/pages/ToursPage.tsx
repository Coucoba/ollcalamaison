import { useState } from "react";
import { useFindTours } from "../hooks/useTour";
import { useNavigate } from "react-router-dom";
import { Box, Button, Typography, Modal, FormControl, FormLabel, Input, FormHelperText, Switch } from "@mui/material";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import dayjs from "dayjs";
import { TourDto } from "../api/type";
import { Controller, useForm } from "react-hook-form";
import { DatePicker } from "@mui/x-date-pickers";

const columns: GridColDef[] = [
    { field: 'id', headerName: 'ID', minWidth: 90, flex:0.5},
    {
      field: 'name',
      headerName: 'Nom',
      minWidth: 200,
      flex:2
    },
    {
      field: 'startDate',
      headerName: 'Date de début',
      minWidth: 200,
      flex:2,
      valueFormatter(params) {
        return dayjs(params.value).format("DD/MM/YYYY HH:mm")
    },
    },
    {
        field: 'endDate',
        headerName: 'Date de fin',
        minWidth: 200,
        flex:2,
        valueFormatter(params) {
            return dayjs(params.value).format("DD/MM/YYYY HH:mm")
        },
    },
    {
        field: 'deliveries',
        headerName: "Nombre de courses",
        minWidth: 200,
        flex: 2,
        valueFormatter(params) {
            return params.value.length;
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

export function ToursPage() {
    const {data : result} = useFindTours();

    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const { control, formState, handleSubmit } = useForm({defaultValues: { name: "", startDate: dayjs(), endDate: dayjs().add(1, "day")}});
    const navigate = useNavigate();

    
    return (
        <Box display="flex" flexDirection="column" justifyContent="center" alignItems="center" height="100%">
            <Typography variant="body1" textAlign="center" justifyContent="center">
                Gestion des Tournées
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
                    getRowId={(row:TourDto) => row.id}
                    onRowClick={(row) => console.log(result)}
                />
                <Button sx={{margin:3}} variant="contained" onClick={handleOpen}>Nouvelle Tournées</Button>
            </Box>

            <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
            <Typography align="center" variant="h5">Création d'une tournée</Typography>
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
                        render={({field}) => (<Input placeholder="Tour Name" {...field}/>)}
                        rules={{
                            required: {
                                value: true,
                                message: "Champ obligatoire"
                            }
                        }}
                    />
                    <FormHelperText>{formState.errors.name?.message}</FormHelperText>
                </FormControl>
                <FormControl error={!!formState.errors.startDate}>
                    <FormLabel>Date de début</FormLabel>
                    <Controller 
                        control={control} 
                        name="startDate" 
                        render={({field}) => (<DatePicker defaultValue={dayjs()} disablePast {...field}/>)}
                    />
                </FormControl>
                <FormControl error={!!formState.errors.endDate}>
                    <FormLabel>Date de fin</FormLabel>
                    <Controller 
                        control={control} 
                        name="endDate" 
                        render={({field}) => (<DatePicker defaultValue={dayjs().add(1, "day")} disablePast {...field}/>)}
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