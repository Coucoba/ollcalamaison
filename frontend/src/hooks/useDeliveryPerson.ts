import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

import { getAllDeliveryPerson, getDeliveryPersonDetail, createDeliveryPerson, DeliveryPersonCreationRequest, updateDeliveryPersonById, deleteDeliveryPersonById } from "./../api/deliveryPerson";

export function useSearchDeliveryPerson(){
    return useQuery({
        queryKey: ["delivrerypersons"],
        queryFn: () => getAllDeliveryPerson()
    });
}

export function useCreateDeliveryPerson(){
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (body: DeliveryPersonCreationRequest) => createDeliveryPerson(body),
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ["delivrerypersons"]})
        }
    })
}

export function useFindDeliveryPersonById(id: number) {
    return useQuery({
        queryKey: ["delivrerypersons", id],
        queryFn: () => getDeliveryPersonDetail(id)
    });
}

export function useUpdateDeliveryPerson(id: number){
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (body: DeliveryPersonCreationRequest) => updateDeliveryPersonById(id ,body),
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ["delivrerypersons"]})
        }
    })
}

export function useDeleteDeliveryPerson(id: number){
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: () => deleteDeliveryPersonById(id),
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ["delivrerypersons"]})
        }
    })
}