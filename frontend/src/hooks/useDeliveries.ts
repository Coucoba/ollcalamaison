import { useQuery } from "@tanstack/react-query";

import { getAllDeliveries } from "./../api/deliveries";

export function useFindDeliveries(){
    return useQuery({
        queryKey: ["deliveries"],
        queryFn: () => getAllDeliveries()
    });
}