import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Trip } from 'src/app/models/trip';
import { EnterpriseService } from 'src/app/service/enterprise.service';
import { PaymentService } from 'src/app/service/payment.service';
import { TripService } from 'src/app/service/trip.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-stripe-payment-modal',
  templateUrl: './stripe-payment-modal.component.html',
  styleUrls: ['./stripe-payment-modal.component.css']
})
export class StripePaymentModalComponent implements OnInit {

  id: string;
  name: string;
  description: string;
  price: number;

  trips : Trip[] = [];
  userId:number;

  constructor(private paymentService: PaymentService,private tripService: TripService,private enterpriseService : EnterpriseService,private toastr: ToastrService,
    public dialogo: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public mensaje: string) { }
 

    cerrarDialogo(): void {
      this.paymentService.delete(this.id).subscribe();
      this.dialogo.close(false);
    }
    confirmado(): void {
      this.paymentService.confirm(this.id).subscribe(
        data => {
          this.toastr.success('Pago realizado Correctamente', 'Pago', {
            timeOut: 3000,
          });
        }, err => {
          this.toastr.error('Error en el pago', 'Pago', {
            timeOut: 3000,
          });
        }
      );
    

      this.trips.forEach(element => {

        this.paymentService.newPaymentStripe().subscribe(
          data => {
            let paymenId: number = data.id
            console.log(data)
            this.paymentService.putPaymentUserId(this.userId, data.id).subscribe(
              data => {
                this.tripService.putTripWithPayment(paymenId, element.id).subscribe(
                  data => {
                    window.location.reload()

                    
                  }
                  
                )


              }
            )

          }
        )
      });

      this.dialogo.close(true);
    }

    ngOnInit(): void {
      this.getAllTrips() 
    }

    getAllTrips() {
      this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
        data => {
          this.userId = data.id;
          
          this.tripService.getTripsInCartControllerCheckout(this.userId).subscribe(
            data => {
              this.trips = data;
              
            }
          )
        }
      )
    }
}
