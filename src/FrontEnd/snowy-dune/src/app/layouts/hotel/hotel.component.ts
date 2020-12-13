import { trigger, transition, style, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ConfirmDialogStationComponent } from 'src/app/components/confirm-dialog-station/confirm-dialog-station.component';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { Comentario } from 'src/app/models/comment';
import { Hotel } from 'src/app/models/hotel';
import { CommentService } from 'src/app/service/comment.service';
import { HotelService } from 'src/app/service/hotel.service';

@Component({
  selector: 'app-hotel',
  templateUrl: './hotel.component.html',
  styleUrls: ['./hotel.component.css'],
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
export class HotelComponent implements OnInit {

  hotels: Hotel[];
  comments: Comentario[];
  
  searchText;
  user: string[] = [];
  currentStars: number;
  previouStationStars: number;
  minPrice: number = 0;
  maxPrice: number = 9999;
  stars: number = null;

  //Pagination
  thePageNumber: number = 1;
  thePageSize:number = 8;
  theTotalElements: number = 0;

  constructor(private hotelService: HotelService, private router: Router, public dialogo: MatDialog, private commentService: CommentService) { }

  ngOnInit(): void {

    this.loadHotel();

  }


  loadHotel(): void {
    
    let country: string =  sessionStorage.getItem('Station');

    this.hotelService.getHotelByCountryListPaginate(this.thePageNumber - 1,
      this.thePageSize,
      country).subscribe(this.processResult());
    
  }

  validateInfo(nombre,hotelId){

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de elegir el hotel ${nombre}?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){
        window.sessionStorage.setItem('HotelId', hotelId)
        this.router.navigate(['/Classes']);
      }
    })
    
  }

  loadComments(id){
    
    this.commentService.getCommentByHotelIdPaginated(0,id).subscribe(
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

  updateHotelPriceSelect(price: number){

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

    this.hotelService.getHotelByStarsPriceListPaginate(this.thePageNumber - 1,
      this.thePageSize,
      country,this.minPrice,this.maxPrice,this.currentStars).subscribe(this.processResult());
   

  }


  updateHotelStarsSelect(stars: number){
    console.log(stars)


    if(this.previouStationStars != stars){
      this.thePageNumber = 1;
      this.currentStars = stars;
    }

    this.previouStationStars = stars;

    let country: string =  sessionStorage.getItem('Station');

    this.hotelService.getHotelByStarsPriceListPaginate(this.thePageNumber - 1,
      this.thePageSize,
      country,this.minPrice,this.maxPrice,this.currentStars).subscribe(this.processResult());
  }

  backToStation() {
    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro que quieres volver?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        window.sessionStorage.removeItem('HotelId');
        this.router.navigate(['/Station']);

      }
    })
  }

  continueToClasses() {
    if (window.sessionStorage.getItem('HotelId') == undefined){
      this.dialogo.open(ConfirmDialogComponent, {
        data:`¿Estás seguro que quieres continuar? No has seleccionado ningun hotel`
      })
      .afterClosed()
      .subscribe((confirmado:Boolean) => {
        if (confirmado){
  
          window.sessionStorage.removeItem('HotelId');
          this.router.navigate(['/Classes']);
  
        }
      })
    }
  }

  processResult(){
    return data => {
      this.hotels = data._embedded.hotel;
      console.log(this.hotels);
      this.thePageNumber = data.page.number +1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements;
    };
  }

}
