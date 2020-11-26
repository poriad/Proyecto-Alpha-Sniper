export class Hotel {
    id?: number;
    price: number;
    name: string;
    phone: string;
    email: string;
    location: string;
    country: string;
    urlImage: string;
    activated: number;
    stars: number;

    constructor(id: number, price: number, name: string, phone: string,email: string, location: string, country: string, urlImagen: string, activated: number,stars: number){
        this.id = id;
        this.price = price;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.country = country;
        this.urlImage = urlImagen;
        this.activated = activated;
        this.stars = stars;
    }
}