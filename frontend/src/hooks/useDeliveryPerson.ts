import { useQuery } from "@tanstack/react-query";
import { GetDeliveryPersonRequest } from "../api/deliveryPerson";

import {
    getAllDeliveryPerson
} from "./../api/deliveryPerson"

export function useSearchDeliveryPerson(){
    return useQuery({
        queryKey: ["delivrerypersons"],
        queryFn: () => getAllDeliveryPerson()
    });
}