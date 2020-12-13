import { trigger, transition, style, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ConfirmDialogStationComponent } from 'src/app/components/confirm-dialog-station/confirm-dialog-station.component';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { CarRental } from 'src/app/models/car-rental';
import { Comentario } from 'src/app/models/comment';
import { CarRentalService } from 'src/app/service/car-rental.service';
import { CommentService } from 'src/app/service/comment.service';

@Component({
  selector: 'app-car-rental',
  templateUrl: './car-rental.component.html',
  styleUrls: ['./car-rental.component.css'],
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
export class CarRentalComponent implements OnInit {

  carRentals: CarRental[];
  comments: Comentario[];
  
  searchText;
  user: string[] = [];
  currentStars: number = 4;
  previouStationStars: number = 4;
  minPrice: number = 0;
  maxPrice: number = 9999;

  //Pagination
  thePageNumber: number = 1;
  thePageSize:number = 8;
  theTotalElements: number = 0;

  constructor(private carRentalService: CarRentalService, private router: Router, public dialogo: MatDialog, private commentService: CommentService) { }

  ngOnInit(): void {

    this.loadCarRental();

  }

  loadCarRental(): void {
    
    let country: string =  sessionStorage.getItem('Station');

    this.carRentalService.getCarRentalByCountryListPaginate(this.thePageNumber - 1,
      this.thePageSize,
      country).subscribe(this.processResult());
    
  }

  loadComments(id){
    
    this.commentService.getCommentByCarRentalIdPaginated(0,id).subscribe(
      data => {
        this.comments = data._embedded.comment;

        this.comments.forEach(element => {

          this.commentService.getCommentByUserId(element.id).subscribe(
            data => {
              this.user.push(data.username);
              
            }
          )
          
        });
      }
    )
  }

  validateInfo(nombre,carRentalId){

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de elegir el alquiler en ${nombre}?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){
        window.sessionStorage.setItem('CarRentalId', carRentalId)
        this.router.navigate(['/Trip']);
      }
    })
    
  }

  updateCarRentalPriceSelect(price: number){

    var country: string =  sessionStorage.getItem('Station');
    

    switch (+price) {
      case 1: 
        this.minPrice = 0;
        this.maxPrice = 30;
        break;
      
      case 2: 
      this.minPrice  = 31;
      this.maxPrice = 60;
        break;
      
      case 3: 
      this.minPrice  = 61;
      this.maxPrice = 100;
        break;
      
      case 4: 
      this.minPrice  = 101;
      this.maxPrice = 200;
        break;
      
      case 5: 
      this.minPrice  = 201;
      this.maxPrice = 9999;
        break;
      default:{
          this.minPrice  = 0;
          this.maxPrice = 9999;
        }
    }

    this.carRentalService.getCarRentalByLocationPriceListPaginate(this.thePageNumber - 1,
      this.thePageSize,
      country,this.minPrice,this.maxPrice).subscribe(this.processResult());
   

  }

  backToSkiMaterial() {
    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro que quieres volver?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        window.sessionStorage.removeItem('CarRentalId');
        this.router.navigate(['/SkiMaterial']);

      }
    })
  }

  continueToCarRental() {

    if(window.sessionStorage.getItem('CarRentalId') == undefined && window.sessionStorage.getItem('SkiMaterialId') == undefined && window.sessionStorage.getItem('HotelId') == undefined && window.sessionStorage.getItem('ClassesId') == undefined) {

      this.dialogo.open(ConfirmDialogStationComponent, {
        data:`No has seleccionado ningún servicio. No puedes continuar`
      })

    } else if (window.sessionStorage.getItem('CarRentalId') == undefined){
      this.dialogo.open(ConfirmDialogComponent, {
        data:`¿Estás seguro que quieres continuar? No has seleccionado ningun alquiler de vehículo`
      })
      .afterClosed()
      .subscribe((confirmado:Boolean) => {
        if (confirmado){
  
          window.sessionStorage.removeItem('CarRentalId');
          this.router.navigate(['/Trip']);
  
        }
      })
    }

  }

  processResult(){
    return data => {
      this.carRentals = data._embedded.carRental;
      console.log(this.carRentals)
      this.thePageNumber = data.page.number +1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements;
    };
  }

}
