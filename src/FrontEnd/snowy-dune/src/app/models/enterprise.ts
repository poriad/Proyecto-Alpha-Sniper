export class Enterprise {
    nomComercial: string;
    nif: string;
    cnae: string;
    activity: string;
    location: string;
    enterprisePhone: string;
    enterpriseEmail:string;

    constructor(nomComercial: string,
        nif: string,
        cnae: string,
        activity: string,
        location: string,
        enterprisePhone: string,
        enterpriseEmail:string){
            this.nomComercial = nomComercial;
            this.nif = nif;
            this.cnae = cnae;
            this.activity = activity;
            this.location = location;
            this.enterprisePhone = enterprisePhone;
            this.enterpriseEmail = enterpriseEmail;
    }
}
