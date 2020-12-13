import { Hotel } from '../models/hotel';

export interface GetResponseHotel {
    _embedded: {
      hotel: Hotel[];
    },
    page: {
      size: number;
      totalElements: number;
      totalPages: number;
      number: number
    }
  
  }
  
  export interface HotelDto {
    price: number;
    description: string;
    name: string;
    phone: string;
    email: string;
    location: string;
    country: string;
    urlImages: string;
    activated: number;
    stars: number;
  }
  