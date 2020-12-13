import { trigger, transition, style, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { CarRental } from 'src/app/models/car-rental';
import { Classes } from 'src/app/models/classes';
import { Hotel } from 'src/app/models/hotel';
import { SkiMaterial } from 'src/app/models/ski-material';
import { Station } from 'src/app/models/station';
import { CarRentalService } from 'src/app/service/car-rental.service';
import { CartService } from 'src/app/service/cart.service';
import { ClassesService } from 'src/app/service/classes.service';
import { EnterpriseService } from 'src/app/service/enterprise.service';
import { HotelService } from 'src/app/service/hotel.service';
import { SkiMaterialService } from 'src/app/service/ski-material.service';
import { StationService } from 'src/app/service/station.service';
import { TripService } from 'src/app/service/trip.service';

@Component({
  selector: 'app-trip',
  templateUrl: './trip.component.html',
  styleUrls: ['./trip.component.css'],
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
export class TripComponent implements OnInit {

  station: Station;
  hotel: Hotel;
  hotelPrice: number;
  classes: Classes;
  classesPrice: number;
  skiMaterial: SkiMaterial;
  skiMaterialPrice: number;
  carRental: CarRental;
  carRentalPrice: number;
  userId: number;
  cartId: number;
  entryDate: string;
  numDays: number;
  numPerson: number;
  totalPrice: number = 0;
  stationId: number;
  classesId: number;
  hotelId: number;
  skiMaterialId: number;
  carRentalId: number;
  

  constructor(private stationService: StationService, private hotelService: HotelService, private classesService: ClassesService, private skiMaterialService: SkiMaterialService, private carRentalService: CarRentalService
    ,public dialogo: MatDialog,private router: Router,
    private enterpriseService: EnterpriseService, private tripService: TripService, private cartService: CartService) { }

  ngOnInit(): void {

    this.getTripDetails();

  }

  getTripDetails(){
    
    this.entryDate = window.sessionStorage.getItem('EntryDate');
    this.numDays = +window.sessionStorage.getItem('NumberDays');
    this.numPerson = +window.sessionStorage.getItem('PersonNumber');

    this.stationId = +window.sessionStorage.getItem('StationId');
    this.classesId = +window.sessionStorage.getItem('ClassesId');
    this.hotelId = +window.sessionStorage.getItem('HotelId');
    this.skiMaterialId = +window.sessionStorage.getItem('SkiMaterialId');
    this.carRentalId = +window.sessionStorage.getItem('CarRentalId');


    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.userId = data.id;

        this.cartService.getCartByUserId(this.userId).subscribe(
          data => {
            this.cartId = data;
          }
        )
      }
    )

    this.stationService.getStationById(this.stationId).subscribe(
      data => {
        this.station = data;
      }
    );
    
    if (this.hotelId != 0){

      this.hotelService.getHotelById(this.hotelId).subscribe(
        data => {
          this.hotel = data;
          this.hotelPrice = data.priceDay * this.numDays * this.numPerson;
          this.totalPrice = this.totalPrice + this.hotelPrice;
        }
      );
    }

    if (this.classesId != 0){
      
      this.classesService.getClassesById(this.classesId).subscribe(
        data => {
          this.classes = data;
          this.classesPrice = data.priceHour * this.numDays * this.numPerson;
          this.totalPrice = this.totalPrice + this.classesPrice;
        }
      );
    }

    if (this.skiMaterialId != 0){
    this.skiMaterialService.getSkiMaterialById(this.skiMaterialId).subscribe(
      data => {
        this.skiMaterial = data;
        this.skiMaterialPrice = data.priceDay * this.numDays * this.numPerson;
        this.totalPrice = this.totalPrice + this.skiMaterialPrice;

      }
    );
    }

    if (this.carRentalId != 0){
    this.carRentalService.getCarRentalById(this.carRentalId).subscribe(
      data => {
        this.carRental = data;
        this.carRentalPrice = data.price * this.numDays * this.numPerson;
        this.totalPrice = this.totalPrice + this.carRentalPrice;

      }
    );
    }
  }

  createTrip(){

    this.dialogo
      .open(ConfirmDialogComponent, {
        data: `Si continuas, el viaje se creará y podrás verlo en el carrito`,
      })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {

          let trip = <TripDto>{
            totalPrice: this.totalPrice,
            totalDays: this.numDays,
            entryDate: this.entryDate,
            totalPersons: this.numPerson
          };

          this.tripService.newTrip(trip).subscribe(
            data => {
              let hotelId;
              let classesId;
              let skiMaterialId;
              let carRentalId;
              

              if(this.hotel == undefined){
                this.hotel = null;
                hotelId = "";
              } else {
                hotelId = this.hotel.id
              }

              if(this.classes == undefined){
                this.classes = null;
                classesId = "";
              } else {
                classesId = this.classes.id
              }

              if(this.skiMaterial == undefined){
                this.skiMaterial = null;
                skiMaterialId="";
              } else {
                skiMaterialId = this.skiMaterial.id
              }

              if(this.carRental == undefined){
                this.carRental = null;
                carRentalId="";
              } else {
                carRentalId = this.carRental.id
              }
              
              this.tripService.putTrip(data.id,this.userId,this.station.id, hotelId, classesId, skiMaterialId, carRentalId,this.cartId).subscribe(
                data => {
                  

                  this.router.navigate(['/Cart']);
                }, err => {
                  console.log(err)
                }
              )

            }, err => {
              console.log(err);
            }, () => {
              
            }
          )

        }
      })
  }
}

export interface TripDto {
  totalPrice: number;
  totalDays: number;
  entryDate: string;
  totalPersons: number
}
