export type SearchResultDto<T> = {
    data: T[];
    itemsPerPage: number;
    itemCount: number;
    page: number;
    pageCount: number;
}

export type DeliveryDto = {
    id: number;
    pickupAddress: string;
    depositAddress: string;
    tour: TourDto
}

export type TourDto = {
    id: number;
    name: string;
    startDate: string;
    endDate: string;
    deliveryPerson: DeliveryPersonDto | null;
    deliveries: DeliveryDto[] | null;
}

export type DeliveryPersonDto = {
    id: number;
    name: string;
    available: boolean;
    creationDate: string;
    numberOfDeliveries: number;
    numberOfTour: number;
}