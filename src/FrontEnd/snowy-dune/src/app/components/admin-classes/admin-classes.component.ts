import { Component, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Classes } from 'src/app/models/classes';
import { ClassesService } from 'src/app/service/classes.service';
import { Header } from 'src/app/utils/header';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-admin-classes',
  templateUrl: './admin-classes.component.html',
  styleUrls: ['./admin-classes.component.css']
})
export class AdminClassesComponent implements OnInit {
  @ViewChildren('closebutton') closebutton;

  header: Header[] = [{title:"Nombre",id:"name"},{title:"Localizacion",id:"location"},{title:"Teléfono",id:"phone"}];

  classes: Classes[] = [];
  class: Classes;
  
  searchText;
  updateMessage: string = "";
  deleteMessage: string = "";
  isUpdateFail: boolean;
  isDeleteFail: boolean;
  submitted: boolean = false;
  submittedDelete: boolean = false;

  thePageNumber: number = 1;
  thePageSize:number = 8;
  theTotalElements: number = 0;
  columnName:string = "";
  order:string = "asc";

  constructor(private classesService:ClassesService, public dialogo: MatDialog) { }

  ngOnInit(): void {

    this.orderClassesList();

  }

  updateStatusClasses(id): void{

    this.submitted = true;
    this.iterateChildrenButton();

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        this.classesService.putClassesToActive(id).subscribe(
          data => {
            this.isUpdateFail = true;
            this.updateMessage = "Clase activada";
            this.orderClassesList();
          }, err => {
            this.isUpdateFail = false;
            
            this.updateMessage = "La clase no se ha podido actualizar";
          });
    
          setTimeout( () => { 
            this.submitted = false;
            this.updateMessage = "";
           },3000);

      }
    })
    
  }

  deleteClasses(id): void {

    this.submittedDelete = true;
    this.iterateChildrenButton();

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        this.classesService.deleteClass(id).subscribe(
          data => {
            this.isDeleteFail = true;
            this.deleteMessage = "Clase pasada a Inactiva";
            this.orderClassesList();
          }, err => {
            this.isDeleteFail = false;
            this.deleteMessage = "La clase no se ha podido actualizar";
          });
    
          setTimeout( () => { 
            this.submittedDelete = false;
            this.deleteMessage = "";
           },3000);
      }
    })
    
  }

  updateOrderListClasses(columnName:string){

    if (this.columnName == columnName){
      this.toggleOrder();
    } 
   
    this.columnName = columnName;
    
    console.log(this.columnName);
    //this.thePageNumber = 1;
    this.orderClassesList();
  }
  orderClassesList(){
    this.classesService.getClassesListPaginatedSorted(this.thePageNumber - 1,
      this.thePageSize,this.columnName,this.order).subscribe(this.processResult());
  }

  toggleOrder(){
    if (this.order == "desc"){
      this.order = "asc";
    } else {
      this.order = "desc";
    }
  }

  processResult(){
    return data => {
      this.classes = data._embedded.classes;
      this.thePageNumber = data.page.number +1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements
    };
  }

  iterateChildrenButton(){
    this.closebutton.forEach(element => {
      element.nativeElement.click();
    });
  }
}
