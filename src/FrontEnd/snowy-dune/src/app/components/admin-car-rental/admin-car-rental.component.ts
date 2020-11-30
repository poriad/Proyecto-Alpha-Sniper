import { Component, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CarRental } from 'src/app/models/car-rental';
import { CarRentalService } from 'src/app/service/car-rental.service';
import { Header } from 'src/app/utils/header';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-admin-car-rental',
  templateUrl: './admin-car-rental.component.html',
  styleUrls: ['./admin-car-rental.component.css']
})
export class AdminCarRentalComponent implements OnInit {
  @ViewChildren('closebutton') closebutton;

  header: Header[] = [{title:"Nombre",id:"name"},{title:"Localizacion",id:"location"},{title:"Teléfono",id:"phone"}];

  carRentals: CarRental[] = [];
  carRental: CarRental;
  
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
  
  constructor(private carRentalService: CarRentalService, public dialogo: MatDialog) { }

  ngOnInit(): void {

    this.orderCarRentalList();

  }

  updateStatusCarRental(id): void{

    this.submitted = true;
    this.iterateChildrenButton();

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        this.carRentalService.putCarRentalToActive(id).subscribe(
     
          data => {
            this.isUpdateFail = true;
            this.updateMessage = "Servicio activado";
            this.orderCarRentalList();
          }, err => {
            this.isUpdateFail = false;
            this.updateMessage = "El servicio de vehículo no se ha podido actualizar";
            this.orderCarRentalList();
          });
    
          setTimeout( () => { 
            this.submitted = false;
            this.updateMessage = "";
           },3000);

      }
    })

    
  }

  deleteCarRental(id): void {

    this.submittedDelete = true;
    this.iterateChildrenButton();

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        this.carRentalService.deleteCarRental(id).subscribe(
          data => {
            this.isDeleteFail = true;
            this.deleteMessage = "Servicio pasado a Inactivo";
            this.orderCarRentalList();
          }, err => {
            this.isDeleteFail = false;
            this.deleteMessage = "El servicio de vehñiculo no se ha podido borrar";
            this.orderCarRentalList();
          });
    
          setTimeout( () => { 
            this.submittedDelete = false;
            this.deleteMessage = "";
           },3000);

      }
    })

    
  }

  updateOrderListCarRental(columnName:string){

    if (this.columnName == columnName){
      this.toggleOrder();
    } 
   
    this.columnName = columnName;
    
    console.log(this.columnName);
    //this.thePageNumber = 1;
    this.orderCarRentalList();
  }


  orderCarRentalList(){
    this.carRentalService.getCarRentalListPaginatedSorted(this.thePageNumber - 1,
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
      this.carRentals = data._embedded.carRental;
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
