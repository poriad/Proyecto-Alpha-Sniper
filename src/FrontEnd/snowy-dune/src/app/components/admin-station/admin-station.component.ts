import { Component, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Station } from 'src/app/models/station';
import { StationService } from 'src/app/service/station.service';
import { Header } from 'src/app/utils/header';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-admin-station',
  templateUrl: './admin-station.component.html',
  styleUrls: ['./admin-station.component.css']
})
export class AdminStationComponent implements OnInit {
  
  @ViewChildren('closebutton') closebutton;

  header: Header[] = [{title:"Nombre",id:"name"},{title:"Localizacion",id:"location"},{title:"Teléfono",id:"phone"}];

  resorts: Station[] = [];
  resort: Station;
  
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
  
  constructor(private stationService: StationService,public dialogo: MatDialog) { }

  ngOnInit(): void {

    this.orderStationList();

  }

  updateStatusStation(id): void{

    this.submitted = true;
    this.iterateChildrenButton();
    
    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        this.stationService.putStationToActive(id).subscribe(
          data => {
            this.isUpdateFail = true;
            this.updateMessage = "Estación activada";
            this.orderStationList();
          }, err => {
            this.isUpdateFail = false;
            this.updateMessage = "La estación no se ha podido actualizar";
          });
    
          setTimeout( () => { 
            this.submitted = false;
            this.updateMessage = "";
           },3000);

      }
    })

    
  }

  deleteStation(id): void {

    this.submittedDelete = true;
    this.iterateChildrenButton();

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){
        this.stationService.deleteStation(id).subscribe(
          data => {
            this.isDeleteFail = true;
            this.deleteMessage = "Estación pasada a Inactiva";
            this.orderStationList();
          }, err => {
            this.isDeleteFail = false;
            this.deleteMessage = "La estación no se ha podido actualizar";
          });
    
          setTimeout( () => { 
            this.submittedDelete = false;
            this.deleteMessage = "";
           },3000);
      }
    })

    
  }

  updateOrderListStation(columnName:string){

    if (this.columnName == columnName){
      this.toggleOrder();
    } 
   
    this.columnName = columnName;
    
    console.log(this.columnName);
    //this.thePageNumber = 1;
    this.orderStationList();
  }
  orderStationList(){
    this.stationService.getStationListPaginatedSorted(this.thePageNumber - 1,
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
      this.resorts = data._embedded.station;
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
