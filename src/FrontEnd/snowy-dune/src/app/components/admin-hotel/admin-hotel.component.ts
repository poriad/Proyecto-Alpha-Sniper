import { Component, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Hotel } from 'src/app/models/hotel';
import { HotelService } from 'src/app/service/hotel.service';
import { Header } from 'src/app/utils/header';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

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

  constructor(private hotelService:HotelService,private toastr: ToastrService,public dialogo: MatDialog) { }

  ngOnInit(): void {
    this.orderHotelList();
  }

  updateStatusHotel(id): void{

    this.submitted = true;
    this.iterateChildrenButton();

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        this.hotelService.putHotelToActive(id).subscribe(
          data => {
            this.isUpdateFail = true;
            this.toastr.success('Servicio activado', 'Actualización', {
              timeOut: 3000,
            });
            this.orderHotelList();
          }, err => {
            this.isUpdateFail = false;
            this.toastr.error('El servicio no se ha podido actualizar', 'Actualización', {
              timeOut: 3000,
            });
          });
    
            this.submitted = false;
      }
    })
    
  }

  deleteHotel(id): void {

    this.submittedDelete = true;
    this.iterateChildrenButton();

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        this.hotelService.deleteHotel(id).subscribe(
          data => {
            this.isDeleteFail = true;
            this.toastr.success('Servicio pasado a Inactivo', 'Actualización', {
              timeOut: 3000,
            });
            this.orderHotelList();
          }, err => {
            this.isDeleteFail = false;
            this.toastr.error('El servicio no se ha podido actualizar', 'Actualización', {
              timeOut: 3000,
            });
          });
    
            this.submittedDelete = false;

      }
    })

    
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
