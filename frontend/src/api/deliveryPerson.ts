import client from "./client";
import { DeliveryPersonDto } from "./type";

export async function getAllDeliveryPerson() {
    var response = await client.get("delivrerypersons");
    return response.json<DeliveryPersonDto[]>();
}

export type DeliveryPersonCreationRequest = {
    name: String;
    isAvailable: boolean;
};

export async function createDeliveryPerson(data:DeliveryPersonCreationRequest) {
    var response = await client.post("delivrerypersons", {json: data})
    return response.json<DeliveryPersonDto>();
}