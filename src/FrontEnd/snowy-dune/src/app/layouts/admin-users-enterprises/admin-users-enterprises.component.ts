import { Component, ElementRef, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { EnterpriseUser } from 'src/app/models/enterprise-user';
import { Header } from 'src/app/utils/header';
import { NewUser } from 'src/app/models/new-user';
import { AdminService } from 'src/app/service/admin.service';
import { stringify } from 'querystring';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-users-enterprises',
  templateUrl: './admin-users-enterprises.component.html',
  styleUrls: ['./admin-users-enterprises.component.css']
})
export class AdminUsersEnterprisesComponent implements OnInit {
  @ViewChildren('closebutton') closebutton;

  header: Header[] = [{title:"Nombre",id:"firstName"},{title:"Apellidos",id:"lastName"},{title:"Email",id:"email"},{title:"Nombre Usuario",id:"username"}];
  headerEnterprise: Header[] = [{title:"Nombre Comercial",id:"nomComercial"},{title:"NIF",id:"nif"},{title:"CNAE",id:"cnae"},{title:"Actividad",id:"activity"}];

  updateMessage: string = "";
  updateMessageEnterprise: string = "";
  deleteMessage: string = "";
  isUpdateFail: boolean;
  isUpdateEnterpriseFail: boolean;
  isDeleteUserFail: boolean;
  submitted: boolean = false;
  searchText;
  searchTextEnterprise;
  
  users: NewUser[] = [];
  userEnterprise: EnterpriseUser[] = [];
  newUser: NewUser;
  //Pagination
  thePageNumber: number = 1;
  thePageSize:number = 8;
  theTotalElements: number = 0;
  columnName:string = "username";
  order:string = "asc";


  thePageNumberEnterprise: number = 1;
  thePageSizeEnterprise:number = 8;
  theTotalElementsEnterprise: number = 0;
  columnNameEnterprise:string = "username";
  orderEnterprise:string = "asc";

  firstName:string;
  lastName:string;
  email:string;
  userName:string;
  password:string;
  address:string;
  newsletter:number;
  phone:string;

  constructor(private adminService: AdminService, private router: Router) { }

  ngOnInit(): void {

    //this.loadUsers();
    this.orderList();
    this.orderListEnterprise();

  }

  onSubmit(id,firstName, lastName,email,address,username,password,phone,newsletter,enterprise): void{

    if (newsletter == "Si"){
      newsletter = 1;
    } else {
      newsletter = 0
    }
    this.submitted = true;

    this.newUser = new NewUser(firstName,lastName,username,email,password,address,newsletter,enterprise,phone);

    this.adminService.putUserDetails(this.newUser, id).subscribe(
      data => {
        this.isUpdateFail = true;
        this.newUser = data;
        this.closebutton.nativeElement.click();
        this.updateMessage = "Registro actualizado";
      }, err => {
        this.closebutton.nativeElement.click();
        this.isUpdateFail = false;
        this.updateMessage = err.error.mensaje;
      });

      setTimeout( () => { 
        this.submitted = false;
        this.updateMessage = "";
       },2000);
  }

  orderList():void{
    this.adminService.getUserListPaginatedSorted(this.thePageNumber - 1,
      this.thePageSize,this.columnName,this.order).subscribe(this.processResult());
  }

  orderListEnterprise():void{
    this.adminService.getUserEnterpriseListPaginatedSorted(this.thePageNumberEnterprise - 1,
      this.thePageSizeEnterprise,this.columnNameEnterprise,this.orderEnterprise).subscribe(this.processResultEnterprise());
  }

  updateEnterpriseStatus(id: number){
    this.adminService.putEnterpriseStatus(id,1).subscribe(
      data => {
        this.submitted = true;
        this.isUpdateEnterpriseFail = true;
        this.updateMessageEnterprise = "Empresa activada";
        this.iterateChildrenButton();
        this.orderListEnterprise();
        
      }, err => {
        this.iterateChildrenButton();
        this.isUpdateEnterpriseFail = false;
        this.updateMessageEnterprise = "No se ha podido activar";
      });

      setTimeout( () => { 
        this.submitted = false;
        this.updateMessageEnterprise = "";
       },3000);
  }

  deleteEnterpriseStatus(id: number){
    this.adminService.putEnterpriseStatus(id,0).subscribe(
      data => {
        this.submitted = true;
        this.isUpdateEnterpriseFail = true;
        this.updateMessageEnterprise = "Empresa borrada";
        this.iterateChildrenButton();
        this.orderListEnterprise();
        
      }, err => {
        this.iterateChildrenButton();
        this.isUpdateEnterpriseFail = false;
        this.updateMessageEnterprise = "No se ha podido borrada";
      });

      setTimeout( () => { 
        this.submitted = false;
        this.updateMessageEnterprise = "";
       },3000);
  }
  
  deleteUser(id:number): void{
    this.adminService.deleteUser(id,2).subscribe(
      data => {
        this.submitted = true;
        this.isDeleteUserFail = true;
        this.deleteMessage = "Usuario borrado";
        
        this.iterateChildrenButton();
        this.orderList();
      }, err => {
        this.isDeleteUserFail = false;
        this.iterateChildrenButton();
        this.deleteMessage = "No se ha podido borrar";
      });
    
     
    setTimeout( () => { 
      this.submitted = false;
      this.deleteMessage = "";
     },3000);
    
  }

  processResult(){
    return data => {
      this.users = data._embedded.user;
      this.thePageNumber = data.page.number +1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements
    };
  }

  processResultEnterprise(){
    return data => {
      this.userEnterprise = data._embedded.user;
      this.thePageNumberEnterprise = data.page.number +1;
      this.thePageSizeEnterprise = data.page.size;
      this.theTotalElementsEnterprise = data.page.totalElements
    };
  }
  
  toggleOrder(){
    if (this.order == "desc"){
      this.order = "asc";
    } else {
      this.order = "desc";
    }
  }

  updateOrderList(columnName:string){

    if (this.columnName == columnName){
      this.toggleOrder();
    } 
   
    this.columnName = columnName;
    
    console.log(this.columnName);
    //this.thePageNumber = 1;
    this.orderList();
  }

  updateOrderListEnterprise(columnName:string){

    if (this.columnNameEnterprise == columnName){
      this.toggleOrder();
    } 
   
    this.columnNameEnterprise = columnName;
    
    console.log(this.columnNameEnterprise);
    //this.thePageNumber = 1;
    this.orderListEnterprise();
  }


  updatePageSize(pageSize: number){
    this.thePageSize = pageSize;
    this.thePageNumber = 1;
    this.orderList();
  }

  updatePageSizeEnterprise(pageSize: number){
    this.thePageSizeEnterprise = pageSize;
    this.thePageNumberEnterprise = 1;
    this.orderListEnterprise();
  }

  iterateChildrenButton(){
    this.closebutton.forEach(element => {
      element.nativeElement.click();
    });
  }

}

