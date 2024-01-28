import client from "./client";
import { TourDto } from "./type";

export async function getAllTours() {
    var response = await client.get("tours");
    return response.json<TourDto[]>();
};

export type TourCreationRequest = {
    name: string;
    startDate: string;
}

export async function createTour() {
    var response = await client.put("tours/create");
    return response.json<TourDto>();
}

