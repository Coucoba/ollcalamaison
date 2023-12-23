import { Box, Typography } from "@mui/material";
import { DataGrid, GridColDef } from '@mui/x-data-grid';

import {useSearchDeliveryPerson} from "./../hooks/useDeliveryPerson";

const columns: GridColDef[] = [
    { field: 'id', headerName: 'ID', width: 90 },
    {
      field: 'name',
      headerName: 'Name',
      width: 150,
    },
    {
      field: 'isAvailable',
      headerName: 'isAvailable',
      width: 150,
    },
    {
      field: 'age',
      headerName: 'Age',
      type: 'number',
      width: 110,
    },
  ];
  

export function DeliveryPersonPage(){

    const {data : result} = useSearchDeliveryPerson()

    return (
        <Box display="flex" flexDirection="column" justifyContent="center" alignItems="center" height="100%">
            <Typography variant="body1" textAlign="center" justifyContent="center">
                Gestion des Livreurs
            </Typography>
            <Box sx={{ height: 400, width: '100%' }}>
      <DataGrid
        rows={result?.data}
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
      />
    </Box>
        </Box>
    )
}