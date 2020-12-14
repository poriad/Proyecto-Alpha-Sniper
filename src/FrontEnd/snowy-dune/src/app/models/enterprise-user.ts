export class EnterpriseUser {
    id?: number;
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    password: string;
    address: string;
    newsletter: number;
    isEnterprise: number;
    phone: string;
    comercialName: string;
    activity: string;
    location: string;
    enterpriseEmail: string;
    enterprisePhone: string;
    nif: string;
    cnae: string;



    constructor(id: number, firstName: string,
        lastName: string,
        username: string,
        email: string,
        password: string,
        address: string,
        newsletter: number,
        isEnterprise: number,
        phone: string, comercialName: string, nif: string, cnae: string, activity: string, location: string, enterpriseEmail: string, enterprisePhone: string) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.newsletter = newsletter;
        this.isEnterprise = isEnterprise;
        this.phone = phone;
        this.comercialName = comercialName;
        this.activity = activity;
        this.location = location;
        this.enterpriseEmail = enterpriseEmail;
        this.enterprisePhone = enterprisePhone;
        this.nif = nif;
        this.cnae = cnae;


    }
}
