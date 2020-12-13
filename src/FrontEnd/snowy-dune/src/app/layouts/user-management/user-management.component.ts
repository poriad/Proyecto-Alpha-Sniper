import { TripService } from './../../service/trip.service';
import { trigger, transition, style, animate } from '@angular/animations';
import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { NewUser } from 'src/app/models/new-user';
import { AdminService } from 'src/app/service/admin.service';
import { EnterpriseService } from 'src/app/service/enterprise.service';
import { Trip } from 'src/app/models/trip';
import { StationService } from 'src/app/service/station.service';
import { Station } from 'src/app/models/station';
import { ModalUserCommentsComponent } from 'src/app/components/modal-user-comments/modal-user-comments.component';
import * as html2pdf from 'html2pdf.js';
import html2canvas from 'html2canvas';
import {jsPDF} from 'jspdf';


@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css'],
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
export class UserManagementComponent implements OnInit {

  userForm: FormGroup;
  user: NewUser;
  userId: number;
  trips: Trip[];
  pastTrips: Trip[];
  pastTripsComplete: Trip[];
  thisYearTripsComplete: Trip[];
  beforeThisYearTrips: Trip[];
  pastStationTrips: Station[] = [];
  beforeThisYearStatioTrips: Station[] = [];
  userModify: UserSnowy;
  stationsTrip: Station[] = [];
  submitted = false;
  isModifyFail: boolean = false;
  modifyEnabled= false;
  
  
  constructor(private formBuilder: FormBuilder,
     private enterpriseService: EnterpriseService, public dialogo: MatDialog,
      private adminService: AdminService,
      private toastr: ToastrService,
      private tripService: TripService) { }

  ngOnInit(): void {
    this.getUserDetails();
    this.getFutureTrips();
    this.getPastTripsThisYear();
    this.getPastTripsBeforeThisYear();
    this.getPastTripsComplete();
    this.getTripsThisYearComplete();
  }

  get f() { return this.userForm.controls; }

  getUserDetails(){
    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.user = data;
        
        this.userForm = this.formBuilder.group({
      
          firstName: [this.user.firstName, Validators.required],
          lastName: [this.user.lastName, Validators.required],
          address:[this.user.address, Validators.required],
          phone: [this.user.phone, [Validators.minLength(9), Validators.maxLength(9)]],
          email: [this.user.email, [Validators.required, Validators.email]],
          userName: [this.user.username, Validators.required],
          password: ['', [Validators.required, Validators.minLength(6)]],
      })

      this.userForm.get('firstName').disable();
      this.userForm.get('lastName').disable();
      this.userForm.get('userName').disable();
      this.userForm.get('email').disable();
      this.userForm.get('password').disable();
      this.userForm.get('address').disable();
      this.userForm.get('phone').disable();


      }
    );
  }

  getFutureTrips(){

    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.userId = data.id;

        this.tripService.getFutureTrips(this.userId).subscribe(
          data => {
            this.trips = data._embedded.trip;
            
            this.trips.forEach(element => {
    
              this.tripService.getStationTrip(element.id).subscribe(
                data => {
                  this.stationsTrip.push(data);
                }
              )
            }); 
          }
        )
      }
    );
  }

  getPastTripsThisYear(){

    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.userId = data.id;

        this.tripService.getPastTrips(this.userId).subscribe(
          data => {
            this.pastTrips = data._embedded.trip;
            
            this.pastTrips.forEach(element => {
    
              this.tripService.getStationTrip(element.id).subscribe(
                data => {
                  this.pastStationTrips.push(data);
                }
              )
            }); 
          }
    
        )

      }
    );
  }

  getPastTripsBeforeThisYear(){

    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.userId = data.id;

        this.tripService.getTripsDoneLastYear(this.userId).subscribe(
          data => {
            this.beforeThisYearTrips = data._embedded.trip;
            
            this.beforeThisYearTrips.forEach(element => {
    
              this.tripService.getStationTrip(element.id).subscribe(
                data => {
                  this.beforeThisYearStatioTrips.push(data);
                }
              )
            }); 
          }
        )
      }
    );

  }

  enableModify(){
    this.toastr.info('Ya puedes modificar la información', 'Modificación', {
      timeOut: 3000,
    });
    this.modifyEnabled = true;

    this.userForm.get('firstName').enable();
    this.userForm.get('lastName').enable();
    this.userForm.get('userName').enable();
    this.userForm.get('email').enable();
    this.userForm.get('password').enable();
    this.userForm.get('address').enable();
    this.userForm.get('phone').enable();

  }

  disableModify(){

    this.userForm.get('firstName').disable();
    this.userForm.get('lastName').disable();
    this.userForm.get('userName').disable();
    this.userForm.get('email').disable();
    this.userForm.get('password').disable();
    this.userForm.get('address').disable();
    this.userForm.get('phone').disable();

  }

  onSubmit(){

    this.submitted = true;

    if (this.userForm.invalid) {
      return;
  }

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de realizar la modificación?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
          data => {
            
            this.userModify = {
              firstName: this.userForm.get('firstName').value,
              lastName: this.userForm.get('lastName').value,
              username:this.userForm.get('userName').value,
              email: this.userForm.get('email').value,
              password: this.userForm.get('password').value,
              address: this.userForm.get('address').value,
              phone: this.userForm.get('phone').value
            };

            this.adminService.putUserManagement(this.userModify, data.id).subscribe(
              data => {
                this.modifyEnabled = false;
                data = this.userModify;
                this.isModifyFail = true;
                this.disableModify();
                this.toastr.success('Información modificada', 'Modificación', {
                  timeOut: 3000,
                });
              }, error => {
                this.isModifyFail = false;
                this.toastr.error('Error en la modificación', 'Modificación', {
                  timeOut: 3000,
                });
              }
            );

          })

          this.submitted = false;

      }
    })
  }

  getComments(){
    this.dialogo.open(ModalUserCommentsComponent, {
      width: '70vw',
      maxWidth: '100vw',
    })
    .afterOpened().subscribe()
  }



  getPastTripsComplete(){

    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.userId = data.id;

        this.tripService.getPastTripsComplete(this.userId).subscribe(
          data => {
            this.pastTripsComplete = data;
            console.log(this.pastTripsComplete)
            
          }
    
        )

      }
    );
  }

  getTripsThisYearComplete(){

    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.userId = data.id;

        this.tripService.getThisYearTripsComplete(this.userId).subscribe(
          data => {
            this.thisYearTripsComplete = data;
            console.log(this.thisYearTripsComplete)
            
          }
    
        )

      }
    );
  }

  downloadPDF(){
    
    var data = document.getElementById('contentTres');

    var opt = {
      margin:       1,
      filename:     'Informacion_viajes.pdf',
      image:        { type: 'jpg', quality: 0.98 },
      html2canvas:  { scale: 2 },
      jsPDF:        { unit: 'in', format: 'letter', orientation: 'portrait' }
    };
    
    // New Promise-based usage:
    html2pdf(data, opt);

  }

}

export interface UserSnowy {
  
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;
  address: string;
  phone: string;

}
