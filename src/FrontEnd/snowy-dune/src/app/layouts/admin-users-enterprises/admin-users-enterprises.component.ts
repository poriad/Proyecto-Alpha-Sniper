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
  
  updatePageSize(pageSize: number){
    this.thePageSize = pageSize;
    this.thePageNumber = 1;
    this.loadUsers();
  }

}


