export class Classes {
    id?: number;
    priceHour: number;
    name: string;
    phone: string;
    email: string;
    location: string;
    urlImage: string;
    activated: number;

    constructor(id: number, priceHour: number, name: string, phone: string,email: string, location: string, urlImagen: string, activated: number){
        this.id = id;
        this.priceHour = priceHour;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.urlImage = urlImagen;
        this.activated = activated;
    }
}
