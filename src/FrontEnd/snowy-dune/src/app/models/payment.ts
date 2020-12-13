import { Trip } from './trip';

export class Payment {
    id: number;
    paymentAmount: number;
    paymentType: string;
    name: string;
    lastName: string;
    enterpriseName: string;
    nif: string;
    address: number;
    addressAditional: number;
    country: string;
    zipCode: string;
    province: string;
    phone: string;
    email: string;
    trips: Trip[];

}
