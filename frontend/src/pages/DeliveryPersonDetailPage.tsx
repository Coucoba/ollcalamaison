import { useNavigate, useParams } from "react-router-dom"
import { useDeleteDeliveryPerson, useFindDeliveryPersonById, useUpdateDeliveryPerson } from "../hooks/useDeliveryPerson";
import { Box, Button, FormControl, FormHelperText, FormLabel, Input, Switch, TextField, Typography } from "@mui/material";
import { Controller, useForm } from "react-hook-form";
import dayjs from "dayjs";
import { useEffect } from "react";

export function DeliveryPersonDetailPage() {

    const {id} = useParams()

    const {data : result} = useFindDeliveryPersonById(Number(id));

    const {mutate: updateDeliveryPerson } = useUpdateDeliveryPerson(Number(id));

    const {mutate: deleteDeliveryPerson} = useDeleteDeliveryPerson(Number(id));

    const { control, formState, handleSubmit, setValue } = useForm({defaultValues: { name: "", available: true }});
    const navigate = useNavigate();

    useEffect(() => {
        if (typeof result === "undefined") return;
        setValue("name", result.name);
        setValue("available", result.available);
      }, [result, setValue]);

    return (
        <>
            <Typography align="center" variant="h5">Création d'un livreur</Typography>
            <form
                onSubmit={handleSubmit((e) => 
                    updateDeliveryPerson(e, {onSuccess: () => navigate(`/deliverypersons`)})
                )}
            >
                <Box sx={{
                    display: "flex",
                    flexDirection: "column",
                    textAlign: "start",
                    gap: 3,
                }}>
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
                <Typography variant="body1">{dayjs(result?.creationDate).format("DD/MM/YYYY HH:mm")}</Typography>
                <Button variant="contained" color="success" type="submit">Valider</Button>
            <Button variant="contained" color="error" onClick={() => deleteDeliveryPerson(undefined, {onSuccess: () => navigate(`/deliverypersons`)})}>Supprimer</Button>
            </Box>
            </form>
        </>
    )
}