export class SkiMaterial {
    id?: number;
    priceDay: number;
    description:number;
    name: string;
    phone: string;
    email: string;
    location: string;
    country:string;
    urlImage: string;
    activated: number;

    constructor(id: number, priceDay: number, name: string, phone: string,email: string, location: string, urlImagen: string, activated: number){
        this.id = id;
        this.priceDay = priceDay;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.urlImage = urlImagen;
        this.activated = activated;
    }
}
