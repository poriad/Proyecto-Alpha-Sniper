import { Component, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { Station } from 'src/app/models/station';
import { StationService } from 'src/app/service/station.service';
import { Header } from 'src/app/utils/header';

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
  
  constructor(private stationService: StationService) { }

  ngOnInit(): void {

    this.orderStationList();

  }

  updateStatusStation(id): void{
    console.log(id)
    this.submitted = true;

    this.stationService.putStationToActive(id).subscribe(
      data => {
        this.isUpdateFail = true;
        this.updateMessage = "Estación activada";
        this.iterateChildrenButton();
        this.orderStationList();
      }, err => {
        this.isUpdateFail = false;
        this.iterateChildrenButton();
        this.updateMessage = "La estación no se ha podido actualizar";
      });

      setTimeout( () => { 
        this.submitted = false;
        this.updateMessage = "";
       },3000);
  }

  deleteStation(id): void {

    this.submittedDelete = true;

    this.stationService.deleteStation(id).subscribe(
      data => {
        this.isDeleteFail = true;
        this.deleteMessage = "Estación pasada a Inactiva";
        this.iterateChildrenButton();
        this.orderStationList();
      }, err => {
        this.isDeleteFail = false;
        this.iterateChildrenButton();
        this.deleteMessage = "La estación no se ha podido actualizar";
      });

      setTimeout( () => { 
        this.submittedDelete = false;
        this.deleteMessage = "";
       },3000);
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
