import { useMutation, useQuery } from "@tanstack/react-query";
import { getAllTours } from "../api/tour";

export function useFindTours(){
    return useQuery({
        queryKey: ["delivrerypersons"],
        queryFn: () => getAllTours()
    });
}

export function useCreateTour() {
    return useMutation({
        
    })
}