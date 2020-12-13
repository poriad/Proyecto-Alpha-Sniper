import { CarRental } from '../models/car-rental';

export interface GetResponseCarRental {
    _embedded: {
      carRental: CarRental[];
    },
    page: {
      size: number;
      totalElements: number;
      totalPages: number;
      number: number
    }
  
  }

  export interface CarRentalDto {
    price: number;
    description: string;
    name: string;
    phone: string;
    email: string;
    location: string;
    country: string;
    urlImages: string;
    activated: number;
  }
  