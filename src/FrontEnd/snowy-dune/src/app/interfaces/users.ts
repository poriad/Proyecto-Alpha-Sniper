import { EnterpriseUser } from '../models/enterprise-user';
import { NewUser } from "../models/new-user";

export interface GetResponseUsers {
  _embedded: {
    user: NewUser[];
  },
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number
  }

}

export interface GetResponseUsersEnterprise {
  _embedded: {
    user: EnterpriseUser[];
  },
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number
  }

}

export interface GetUser {
  username: string;
}
