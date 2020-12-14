import { Comentario } from '../models/comment';

export interface GetResponseComment {
  _embedded: {
    comment: Comentario[];
  },
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number
  }

}
