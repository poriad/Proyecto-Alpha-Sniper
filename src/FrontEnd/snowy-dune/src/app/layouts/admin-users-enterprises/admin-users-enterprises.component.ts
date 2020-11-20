import { Component, OnInit } from '@angular/core';
import { NewUser } from 'src/app/models/new-user';
import { AdminService } from 'src/app/service/admin.service';

@Component({
  selector: 'app-admin-users-enterprises',
  templateUrl: './admin-users-enterprises.component.html',
  styleUrls: ['./admin-users-enterprises.component.css']
})
export class AdminUsersEnterprisesComponent implements OnInit {

  users: NewUser[] = [];
  newUser: NewUser;
  //Pagination
  thePageNumber: number = 1;
  thePageSize:number = 8;
  theTotalElements: number = 0;

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {

    this.loadUsers();


  }

  modifyUser(id: number): void{
    console.log(id);

  }

  deleteUser(id:number): void{

    console.log("hola")
    this.adminService.deleteUser(id);
    //window.location.reload();
    
  }

  orderListUsername():void{
    this.adminService.getUserListPaginateSortedByUsername(this.thePageNumber - 1,
      this.thePageSize).subscribe(this.processResult());
  }

  orderListFirstName():void{
    this.adminService.getUserListPaginateSortedByFirstName(this.thePageNumber - 1,
      this.thePageSize).subscribe(this.processResult());
  }

  orderListLastName():void{
    this.adminService.getUserListPaginateSortedByLastName(this.thePageNumber - 1,
      this.thePageSize).subscribe(this.processResult());
  }

  orderListEmail():void{
    this.adminService.getUserListPaginateSortedByEmail(this.thePageNumber - 1,
      this.thePageSize).subscribe(this.processResult());
  }

  loadUsers(): void {
    this.adminService.getUserListPaginate(this.thePageNumber - 1,
      this.thePageSize).subscribe(this.processResult());
  }

  processResult(){
    return data => {
      this.users = data._embedded.user;
      this.thePageNumber = data.page.number +1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements
    };
  }
  
  updateOrderListUsername(){
    //this.thePageNumber = 1;
    this.orderListUsername();
  }

  updateOrderListEmail(){
    //this.thePageNumber = 1;
    this.orderListEmail();
  }

  updateOrderListFirstName(){
    //this.thePageNumber = 1;
    this.orderListFirstName();
  }

  updateOrderListLastName(){
    //this.thePageNumber = 1;
    this.orderListLastName();
  }

  updatePageSize(pageSize: number){
    this.thePageSize = pageSize;
    this.thePageNumber = 1;
    this.loadUsers();
  }

}


