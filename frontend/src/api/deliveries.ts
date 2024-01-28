import client from "./client";
import { DeliveryDto } from "./type";

export async function getAllDeliveries() {
    var response = await client.get("deliveries");
    return response.json<DeliveryDto[]>();
};

