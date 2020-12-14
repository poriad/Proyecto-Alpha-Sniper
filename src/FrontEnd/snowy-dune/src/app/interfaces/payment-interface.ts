import { Trip } from '../models/trip';

export interface PaymentDto {

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

export interface PaymentIntentDto {
  token: string;
  description: string;
  amount: number;
  currency: string;
}