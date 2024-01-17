export enum Roles {
    student = 'ROLE_STUDENT',
    teacher = 'ROLE_TEACHER',
    admin = 'ROLE_ADMIN'
  }
  
  export class User {
    id : number;
    roles: Roles;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    pictureUrl: string;
  
    constructor(id : number,email: string, password: string,roles: Roles, firstName : string, lastName : string,pictureUrl: string) {
        this.email = email;
        this.password = password;
        this.roles = Roles.student;
        this.firstName = 'Hossam';
        this.lastName = 'Ahmed';
        this.id = id
        this.pictureUrl = pictureUrl;
      }
  
    toString(): string {
      return `User [roles=${this.roles}, email=${this.email}, password=${this.password}, firstName=${this.firstName}, lastName=${this.lastName}]`;
    }
  }
  