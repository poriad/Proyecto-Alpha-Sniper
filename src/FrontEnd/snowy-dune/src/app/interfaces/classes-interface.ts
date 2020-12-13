import { Classes } from "../models/classes";

export interface GetResponseClasses {
    _embedded: {
      classes: Classes[];
    },
    page: {
      size: number;
      totalElements: number;
      totalPages: number;
      number: number
    }
  
  }
  
  export interface ClassesDto {
    priceHour: number;
    description: string;
    name: string;
    phone: string;
    email: string;
    location: string;
    country: string;
    urlImages: string;
    activated: number;
  }
  