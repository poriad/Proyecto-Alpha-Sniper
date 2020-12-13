import { Forfait } from './forfait';

export class Station {

    id?: number;
    name: string;
    location: string;
    country: string;
    openingDate: Date;
    closingDate: Date;
    description: String;
    urlImages: string;
    activated:number;
    forfaitPrice: Forfait;
    //priceForfait: number;

    constructor(name: string, location: string, country: string,
        openingDate: Date, closingDate: Date, description: string,
        urlImages: string,activated:number, forfaitPrice: Forfait){
        this.name = name;
        this.location = location;
        this.country = country;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.description = description;
        this.urlImages = urlImages;
        this.activated = activated;
        this.forfaitPrice = forfaitPrice;
        //this.priceForfait = priceForfait;
    }



}