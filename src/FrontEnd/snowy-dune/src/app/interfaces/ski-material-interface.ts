import { SkiMaterial } from "../models/ski-material";

export interface GetResponseSkiMaterial {
    _embedded: {
      skiMaterial: SkiMaterial[];
    },
    page: {
      size: number;
      totalElements: number;
      totalPages: number;
      number: number
    }
  }

  export interface SkiMaterialDto {
    priceDay: number;
    description: string;
    name: string;
    phone: string;
    email: string;
    location: string;
    country: string;
    urlImages: string;
    activated: number;
  }
  