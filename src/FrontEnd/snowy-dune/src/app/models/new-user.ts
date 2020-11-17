export class NewUser {
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    password: string;
    address: string;
    newsletter: boolean;
    phone: string
    authorities: string[];

    constructor(firstName: string,
        lastName: string,
        username: string,
        email: string,
        password: string,
        address: string,
        newsletter: boolean,
        phone: string) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.email = email;
            this.password = password;
            this.address = address;
            this.newsletter = newsletter;
            this.phone = phone
        }
}
