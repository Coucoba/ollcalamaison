import client from "./client";
import { DeliveryPersonDto, SearchResultDto } from "./type";

export type GetDeliveryPersonRequest = {
    page?: number;
    size?: number;
    sortBy?: "name" | "isAvailable" | "creation";
    sortDirection?: "asc" | "desc";
}

export async function getAllDeliveryPerson() {
    var response = await client.get("delivrerypersons");
    return response.json<SearchResultDto<DeliveryPersonDto>>();
}