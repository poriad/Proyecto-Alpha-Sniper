export class Comentario {
    id: number;
    date: Date;
    comment: string;


    constructor(id: number, date: Date, comment: string) {
        this.id = id;
        this.date = date;
        this.comment = comment;
    }

}
