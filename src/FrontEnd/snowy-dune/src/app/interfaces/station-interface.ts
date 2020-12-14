import { Station } from "../models/station";

export interface GetResponseStations {
  _embedded: {
    station: Station[];
  },
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number
  }

}