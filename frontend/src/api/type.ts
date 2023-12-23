export type SearchResultDto<T> = {
    data: T[];
    itemsPerPage: number;
    itemCount: number;
    page: number;
    pageCount: number;
}

export type DeliveryDto = {
    id: number;
    pickupAddress: String;
    depositAddress: String;
    tour: TourDto
}

export type TourDto = {
    id: number;
    name: String;
    startDate: String;
    endDate: String;
    deliveryPerson: DeliveryPersonDto | null;
    deliveries: DeliveryDto[] | null;
}

export type DeliveryPersonDto = {
    id: number;
    name: String;
    isAvailable: boolean;
    creationDate: String;
    numberOfDeliveries: number;
    numberOfTour: number;
}