import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

import { getAllDeliveryPerson, createDeliveryPerson, DeliveryPersonCreationRequest } from "./../api/deliveryPerson";

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