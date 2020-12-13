import { Component, ElementRef, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { EnterpriseUser } from 'src/app/models/enterprise-user';
import { Header } from 'src/app/utils/header';
import { NewUser } from 'src/app/models/new-user';
import { AdminService } from 'src/app/service/admin.service';
import { stringify } from 'querystring';
import { Router } from '@angular/router';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { trigger, transition, style, animate } from '@angular/animations';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-admin-users-enterprises',
  templateUrl: './admin-users-enterprises.component.html',
  styleUrls: ['./admin-users-enterprises.component.css'],
  animations: [
    trigger('fade', [      
      transition('void => *', [
        style({opacity: 0}),
        animate(1000, style({opacity: 1}))
      ]),
      transition('* => void', [
        animate(1000, style({opacity: 0}))
      ])
    ])
]
})
export class AdminUsersEnterprisesComponent implements OnInit {
  @ViewChildren('closebutton') closebutton;

  header: Header[] = [{title:"Nombre",id:"firstName"},{title:"Apellidos",id:"lastName"},{title:"Email",id:"email"},{title:"Nombre Usuario",id:"username"}];
  headerEnterprise: Header[] = [{title:"Nombre Comercial",id:"nomComercial"},{title:"NIF",id:"nif"},{title:"CNAE",id:"cnae"},{title:"Actividad",id:"activity"}];

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

  constructor(private adminService: AdminService, private router: Router,public dialogo: MatDialog,private toastr: ToastrService) { }

  ngOnInit(): void {

    //this.loadUsers();
    this.orderList();
    this.orderListEnterprise();

  }

  onSubmit(id,firstName, lastName,email,address,username,password,phone,newsletter,enterprise): void{

    this.submitted = true;

    this.iterateChildrenButton();

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        if (newsletter == "Si"){
          newsletter = 1;
        } else {
          newsletter = 0
        }
        
    
        this.newUser = new NewUser(firstName,lastName,username,email,password,address,newsletter,enterprise,phone);
    
        this.adminService.putUserManagement(this.newUser, id).subscribe(
          data => {
            this.isUpdateFail = true;
            this.newUser = data;
            this.toastr.success('Registro actualizado', 'Actualización', {
              timeOut: 3000,
            });
          }, err => {
            this.isUpdateFail = false;
            this.toastr.error('No se ha podido actualizar', 'Actualización', {
              timeOut: 3000,
            });
          });
    
          this.submitted = false;


      }
    })
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

    this.submitted = true;
    this.iterateChildrenButton();

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){
        this.adminService.putEnterpriseStatus(id,1).subscribe(
        data => {
            this.isUpdateEnterpriseFail = true;
            this.toastr.success('Empresa activada', 'Actualización', {
              timeOut: 3000,
            });
            this.orderListEnterprise();
          }, err => {
            this.iterateChildrenButton();
            this.isUpdateEnterpriseFail = false;
            this.toastr.error('No se ha podido activar', 'Actualización', {
              timeOut: 3000,
            });
          });
            this.submitted = false;

      }
    })

    
  }

  deleteEnterpriseStatus(id: number){

    this.submitted = true;
    this.iterateChildrenButton();

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        this.adminService.putEnterpriseStatus(id,0).subscribe(
          data => {
            this.isUpdateEnterpriseFail = true;
            this.toastr.success('Empresa borrada', 'Actualización', {
              timeOut: 3000,
            });
            this.orderListEnterprise();
          }, err => {
            this.iterateChildrenButton();
            this.isUpdateEnterpriseFail = false;
            this.toastr.error('No se ha podido borrar', 'Actualización', {
              timeOut: 3000,
            });
          });
     
            this.submitted = false;


      }
    })
    
  }
  
  deleteUser(id:number): void{

    this.submitted = true;

    this.iterateChildrenButton();

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        this.adminService.deleteUser(id,2).subscribe(
          data => {
            this.isDeleteUserFail = true;
            this.toastr.success('Usuario borrado', 'Actualización', {
              timeOut: 3000,
            });
            this.orderList();
          }, err => {
            this.isDeleteUserFail = false;
            this.toastr.error('No se ha podido borrar', 'Actualización', {
              timeOut: 3000,
            });
          });
        

          this.submitted = false;



      }
    })

    
    
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

