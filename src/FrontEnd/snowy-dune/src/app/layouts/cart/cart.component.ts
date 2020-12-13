import { trigger, transition, style, animate } from '@angular/animations';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { CarRental } from 'src/app/models/car-rental';
import { Classes } from 'src/app/models/classes';
import { Hotel } from 'src/app/models/hotel';
import { SkiMaterial } from 'src/app/models/ski-material';
import { Station } from 'src/app/models/station';
import { Trip } from 'src/app/models/trip';
import { CarRentalService } from 'src/app/service/car-rental.service';
import { ClassesService } from 'src/app/service/classes.service';
import { EnterpriseService } from 'src/app/service/enterprise.service';
import { HotelService } from 'src/app/service/hotel.service';
import { SkiMaterialService } from 'src/app/service/ski-material.service';
import { StationService } from 'src/app/service/station.service';
import { TripService } from 'src/app/service/trip.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
  encapsulation: ViewEncapsulation.None,
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
export class CartComponent implements OnInit {
  color = "accent";
  trips: Trip[] = [];
  tripsInCheckOut: Trip[] = [];
  stations: Station[] = [];
  hotels: Hotel[] = [];
  classes: Classes[] = [];
  skiMaterials: SkiMaterial[] = [];
  carRentals: CarRental[] = [];
  totalPrice: number = 0;
  userId: number;
  isLinear = false;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;

  constructor(private toastr: ToastrService,private router: Router,
    private enterpriseService: EnterpriseService,  private tripService: TripService,public dialogo: MatDialog,private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.getAllTrips();
  }

  removeTab(index: number,idTrip: number) {

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de borrar el viaje?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){
        
        this.tripService.deleteTrip(idTrip).subscribe(
          data => {
            this.trips.splice(index, 1);
            this.toastr.success('Viaje Borrado Correctamente', 'Borrado', {
              timeOut: 3000,
            });
          }
        )
      }
    })
  }

  addTrip(id: number){
    var element = <HTMLInputElement> document.getElementById("btnTrip");
    
    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de añadir el viaje al proceso de checkout?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        this.tripService.putTripToCheckout(id).subscribe(
          data => {
            this.toastr.success('Viaje añadido al heckout', 'Viaje', {
              timeOut: 3000,
            });
            this.getAllTrips();
          }
        
        );
      }
    })

  }

  goToCheckout(){
    
        this.router.navigate(['/Checkout']);
      
  
  }

  getAllTrips(){

    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.userId = data.id;
        
        this.tripService.getTripsInCartController(this.userId).subscribe(
          data => {
            this.trips = data;
            console.log(this.trips)

            this.trips.forEach(element => {
              this.totalPrice = this.totalPrice + element.totalPrice;

            });
            
          }
        )

        this.tripService.getTripsInCartControllerCheckout(this.userId).subscribe(
          data => {
            this.tripsInCheckOut = data
          })
      }
    )

  }
}

