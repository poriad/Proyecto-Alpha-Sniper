export class Forfait {
    id?: number;
    price: number;
    idStation: number;

    constructor(id: number, price: number, idStation: number) {
        this.id = id;
        this.price = price;
        this.idStation = idStation;
    }
}
