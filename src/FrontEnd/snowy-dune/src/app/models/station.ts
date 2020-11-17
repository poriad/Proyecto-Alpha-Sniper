export class Station {

    id?: number;
    name: string;
    location: string;
    country: string;
    openingDate: Date;
    closingDate: Date;
    description: String;
    urlImages: string;

    constructor(name: string, location: string, country: string,
        openingDate: Date, closingDate: Date, description: string,
        urlImages: string){
        this.name = name;
        this.location = location;
        this.country = country;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.description = description;
        this.urlImages = urlImages;
    }



}