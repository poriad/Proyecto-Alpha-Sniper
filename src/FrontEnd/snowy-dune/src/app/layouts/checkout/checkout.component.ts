import { trigger, transition, style, animate } from '@angular/animations';
import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatDialog } from '@angular/material/dialog';
import { StripeCardElement, StripeCardElementOptions, StripeElement, StripeElements, StripeElementsOptions } from '@stripe/stripe-js';
import { StripeCardComponent, StripeService } from 'ngx-stripe';
import { ToastrService } from 'ngx-toastr';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { StripePaymentModalComponent } from 'src/app/components/stripe-payment-modal/stripe-payment-modal.component';
import { PaymentDto, PaymentIntentDto } from 'src/app/interfaces/payment-interface';
import { Payment } from 'src/app/models/payment';
import { Trip } from 'src/app/models/trip';
import { EnterpriseService } from 'src/app/service/enterprise.service';
import { PaymentService } from 'src/app/service/payment.service';
import { TripService } from 'src/app/service/trip.service';


@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
  encapsulation: ViewEncapsulation.None,
  animations: [
    trigger('fade', [
      transition('void => *', [
        style({ opacity: 0 }),
        animate(1000, style({ opacity: 1 }))
      ]),
      transition('* => void', [
        animate(1000, style({ opacity: 0 }))
      ])
    ])
  ]
})
export class CheckoutComponent implements OnInit {

  countries: string[] = ["Andorra", "Austria", "Alemania", "España", "Francia", "Italia", "Suiza"]
  detailsForm: FormGroup;
  price: number;
  name: string = 'SnowyDune';
  description: string = 'Viaje SnowyDune';
  userId: number;
  trips: Trip[] = [];
  totalPrice: number = 0;
  checkboxInfo: string = "";

  elements: StripeElements;
  card: StripeCardElement;

  cardOptions: StripeCardElementOptions = {
    style: {
      base: {
        iconColor: 'rgba(27, 77, 176, 1)',
        color: 'rgba(27, 77, 176, 1)',
        fontWeight: '300',
        fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
        fontSize: '18px',
        '::placeholder': {
          color: 'rgba(27, 77, 176, 1)'
        }
      }
    }
  };

  elementsOptions: StripeElementsOptions = {
    locale: 'es'
  };


  constructor(private stripeService: StripeService, private paymentService: PaymentService, private enterpriseService: EnterpriseService, private toastr: ToastrService, private formBuilder: FormBuilder, public dialogo: MatDialog, private tripService: TripService) { }

  creditCardForm: FormGroup;

  stripeFrom: FormGroup;


  ngOnInit(): void {
    this.getAllTrips();

    this.detailsForm = this.formBuilder.group({
      name: ['', Validators.required],
      lastName: ['', Validators.required],
      enterprise: [''],
      nif: ['', [Validators.required, Validators.pattern("[0-9]{8}[A-Za-z]{1}")]],
      address: ['', Validators.required],
      addressAditional: [''],
      country: ['', Validators.required],
      zipCode: ['', [Validators.required, Validators.pattern("[0-9]{5}")]],
      province: ['', Validators.required],
      phone: [''],
      email: ['', [Validators.required, Validators.email]],
    })

    this.creditCardForm = this.formBuilder.group({
      cardNumber: ['', Validators.required],
      expiration: ['', Validators.required],
      cvv: ['', Validators.required],

    })

    this.stripeFrom = this.formBuilder.group({
      name: ['', [Validators.required]]
    });


    this.stripeService.elements(this.elementsOptions)
      .subscribe(elements => {
        this.elements = elements;
        // Only mount the element the first time
        if (!this.card) {
          this.card = this.elements.create('card', this.cardOptions);
          this.card.mount('#card-element');
        }
      });


  }

  public errorHandling = (control: string, error: string) => {
    return this.detailsForm.controls[control].hasError(error);
  }

  public errorHandlingCard = (control: string, error: string) => {
    return this.creditCardForm.controls[control].hasError(error);
  }

  onSubmit() {

    if (this.checkboxInfo == 'creditCard') {
      this.buy();

    } else {

      this.dialogo.open(ConfirmDialogComponent, {
        data: `Se va a proceder a realizar el pago.`
      })
        .afterClosed()
        .subscribe((confirmado: Boolean) => {


          if (confirmado) {

            let payment = <PaymentDto>{
              paymentAmount: this.totalPrice,
              paymentType: this.checkboxInfo,
              name: this.detailsForm.controls['name'].value,
              lastName: this.detailsForm.controls['lastName'].value,
              enterpriseName: this.detailsForm.controls['enterprise'].value,
              nif: this.detailsForm.controls['nif'].value,
              address: this.detailsForm.controls['address'].value,
              addressAditional: this.detailsForm.controls['addressAditional'].value,
              country: this.detailsForm.controls['country'].value,
              zipCode: this.detailsForm.controls['zipCode'].value,
              province: this.detailsForm.controls['province'].value,
              phone: this.detailsForm.controls['phone'].value,
              email: this.detailsForm.controls['email'].value,
              trips: this.trips
            }

            this.trips.forEach(element => {

              this.paymentService.newPayment(payment).subscribe(
                data => {
                  let paymenId: number = data.id
                  console.log(data)
                  this.paymentService.putPaymentUserId(this.userId, data.id).subscribe(
                    data => {
                      this.tripService.putTripWithPayment(paymenId, element.id).subscribe(
                        data => {
                          this.getAllTrips();
                          this.detailsForm.reset();
                          this.stripeFrom.reset();
                          this.toastr.success('Pago realizado Correctamente', 'Pago', {
                            timeOut: 3000,
                          });
                        }, err => {
                          this.toastr.error('Error en el pago', 'Pago', {
                            timeOut: 3000,
                          });
                        }
                      )


                    }
                  )

                }
              )
            });
          }

        })
    }
  }

  checkbox(value) {
    this.checkboxInfo = value;
  }

  getAllTrips() {

    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.userId = data.id;

        this.tripService.getTripsInCartControllerCheckout(this.userId).subscribe(
          data => {
            this.trips = data;
            console.log(this.trips)

            this.trips.forEach(element => {
              this.totalPrice = this.totalPrice + element.totalPrice;

            });

          }
        )
      }
    )

  }


  confirmPayment(id: string) {
    this.paymentService.confirm(id).subscribe(
      data => {
        this.toastr.success('Pago realizado Correctamente', 'Pago', {
          timeOut: 3000,
        });
      }, err => {
        this.toastr.error('Error en el pago', 'Pago', {
          timeOut: 3000,
        });
      }
    )
  }

  cancelPayment(id: string) {
    this.paymentService.delete(id).subscribe(
      data => {
        this.toastr.success('Pago cancelado Correctamente', 'Pago', {
          timeOut: 3000,
        });
      }, err => {
        this.toastr.error('El pago no se ha podido cancelar', 'Pago', {
          timeOut: 3000,
        });
      }
    )
  }

  openModal(id: string, name: string, description: string, price: number) {
    const modalRef = this.dialogo.open(StripePaymentModalComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.name = name;
    modalRef.componentInstance.description = description;
    modalRef.componentInstance.price = price;


  }

  buy() {

    const name = this.stripeFrom.get('name').value;
    this.stripeService
      .createToken(this.card, { name })
      .subscribe((result) => {
        if (result.token) {

          const paymentIntentDto: PaymentIntentDto = {
            token: result.token.id,
            amount: this.totalPrice * 100,
            currency: 'EUR',
            description: this.description

          };

          console.log(this.totalPrice)

          this.paymentService.pay(paymentIntentDto).subscribe(
            data => {
              this.openModal(data[`id`], this.name, data[`description`], data[`amount`]);
              this.toastr.info('El pago se encuentra pendiente de confirmación', 'Pago', {
                timeOut: 3000,
              });
            }
          );
        } else if (result.error) {
          this.toastr.error(result.error.message, 'Pago', {
            timeOut: 3000,
          });
        }
      });

  }
}


