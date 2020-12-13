export class Hotel {
    id?: number;
    priceDay: number;
    description:string;
    name: string;
    phone: string;
    email: string;
    location: string;
    country: string;
    urlImages: string;
    activated: number;
    stars: number;

    constructor(id: number, priceDay: number, name: string, phone: string,email: string, location: string, country: string, urlImages: string, activated: number,stars: number){
        this.id = id;
        this.priceDay = priceDay;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.country = country;
        this.urlImages = urlImages;
        this.activated = activated;
        this.stars = stars;
    }
}