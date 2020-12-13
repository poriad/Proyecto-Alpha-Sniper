import { Trip } from '../models/trip';

export interface GetResponseTrip {
    _embedded: {
      trip: Trip[];
    }
  }
  