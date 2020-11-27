import { Component, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { Hotel } from 'src/app/models/hotel';
import { HotelService } from 'src/app/service/hotel.service';
import { Header } from 'src/app/utils/header';

@Component({
  selector: 'app-admin-hotel',
  templateUrl: './admin-hotel.component.html',
  styleUrls: ['./admin-hotel.component.css']
})
export class AdminHotelComponent implements OnInit {

  @ViewChildren('closebuttonhotel') closebuttonhotel;

  header: Header[] = [{title:"Nombre",id:"name"},{title:"Localizacion",id:"location"},{title:"Teléfono",id:"phone"}];

  hotels: Hotel[] = [];
  hotel: Hotel;
  
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

  constructor(private hotelService:HotelService) { }

  ngOnInit(): void {
    this.orderHotelList();
  }

  updateStatusHotel(id): void{

    this.submitted = true;

    this.hotelService.putHotelToActive(id).subscribe(
      data => {
        this.isUpdateFail = true;
        this.updateMessage = "Servicio activado";
        this.iterateChildrenButton();
        
        this.orderHotelList();
      }, err => {
        this.isUpdateFail = false;
        this.iterateChildrenButton();
        this.updateMessage = "El servicio no se ha podido actualizar";
      });

      setTimeout( () => { 
        this.submitted = false;
        this.updateMessage = "";
       },3000);
  }

  deleteHotel(id): void {

    this.submittedDelete = true;

    this.hotelService.deleteHotel(id).subscribe(
      data => {
        this.isDeleteFail = true;
        this.deleteMessage = "Servicio pasado a Inactivo";
        this.iterateChildrenButton();
        this.orderHotelList();
      }, err => {
        this.isDeleteFail = false;
        this.iterateChildrenButton();
        this.deleteMessage = "El servicio no se ha podido actualizar";
      });

      setTimeout( () => { 
        this.submittedDelete = false;
        this.deleteMessage = "";
       },3000);
  }

  updateOrderListHotel(columnName:string){

    if (this.columnName == columnName){
      this.toggleOrder();
    } 
   
    this.columnName = columnName;
    
    console.log(this.columnName);
    //this.thePageNumber = 1;
    this.orderHotelList();
  }
  orderHotelList(){
    this.hotelService.getHotelListPaginatedSorted(this.thePageNumber - 1,
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
      this.hotels = data._embedded.hotel;
      this.thePageNumber = data.page.number +1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements
    };
  }

  iterateChildrenButton(){
    this.closebuttonhotel.forEach(element => {
      element.nativeElement.click();
    });
  }

}