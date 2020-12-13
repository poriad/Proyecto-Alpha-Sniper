export class Classes {
    id?: number;
    priceHour: number;
    description: string;
    name: string;
    phone: string;
    email: string;
    location: string;
    urlImages: string;
    activated: number;

    constructor(id: number, priceHour: number, name: string, phone: string,email: string, location: string, urlImages: string, activated: number){
        this.id = id;
        this.priceHour = priceHour;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.urlImages = urlImages;
        this.activated = activated;
    }
}
