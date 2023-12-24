import client from "./client";
import { DeliveryPersonDto } from "./type";

export async function getAllDeliveryPerson() {
    var response = await client.get("delivrerypersons");
    return response.json<DeliveryPersonDto[]>();
};

export type DeliveryPersonCreationRequest = {
    name: string;
    available: boolean;
};

export async function createDeliveryPerson(data:DeliveryPersonCreationRequest) {
    var response = await client.post("delivrerypersons", {json: data});
    return response.json<DeliveryPersonDto>();
};

export async function getDeliveryPersonDetail(id:number){
    var response = await client.get(`delivrerypersons/${id}`);
    return response.json<DeliveryPersonDto>();
};

export async function updateDeliveryPersonById(id: number, data: DeliveryPersonCreationRequest) {
    console.log(data)
    var response = await client.put(`delivrerypersons/${id}`, {json: data});
    return response.json<DeliveryPersonDto>();
};

export async function deleteDeliveryPersonById(id: number){
    await client.delete(`delivrerypersons/${id}`);
}