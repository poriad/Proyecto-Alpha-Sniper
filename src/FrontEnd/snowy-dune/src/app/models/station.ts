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
    priceForfait: number;

    constructor(name: string, location: string, country: string,
        openingDate: Date, closingDate: Date, description: string,
        urlImages: string,activated:number,priceForfait: number){
        this.name = name;
        this.location = location;
        this.country = country;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.description = description;
        this.urlImages = urlImages;
        this.activated = activated;
        this.priceForfait = priceForfait;
    }



}