import { CarRental } from './car-rental';
import { Classes } from './classes';
import { Hotel } from './hotel';
import { SkiMaterial } from './ski-material';

export class Trip {

    id: number;
    totalPrice: number;
    totalDays: number;
    entryDate: Date;
    totalPersons: number;
    checkout: number;
    hotel: Hotel;
    classes: Classes;
    carRental: CarRental;
    skiMaterial: SkiMaterial;



}