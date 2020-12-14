import { AdminService } from 'src/app/service/admin.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Enterprise } from 'src/app/models/enterprise';
import { NewUser } from 'src/app/models/new-user';
import { EnterpriseService } from 'src/app/service/enterprise.service';
import { TokenService } from 'src/app/service/token.service';
import { EnterpriseListServicesComponent } from '../enterprise-list-services/enterprise-list-services.component';
import { CarRental } from 'src/app/models/car-rental';
import { Classes } from 'src/app/models/classes';
import { Hotel } from 'src/app/models/hotel';
import { SkiMaterial } from 'src/app/models/ski-material';
import { CarRentalService } from 'src/app/service/car-rental.service';
import { ClassesService } from 'src/app/service/classes.service';
import { HotelService } from 'src/app/service/hotel.service';
import { SkiMaterialService } from 'src/app/service/ski-material.service';
import { StationService } from 'src/app/service/station.service';
import { Station } from 'src/app/models/station';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-enterprise-register',
  templateUrl: './enterprise-register.component.html',
  styleUrls: ['./enterprise-register.component.css']
})
export class EnterpriseRegisterComponent implements OnInit {

  enterpriseForm: FormGroup;
  enterprise: Enterprise;
  enterpriseStatus: string;
  inactiveEnterprise: boolean;
  submitted = false;
  isEnterpriseChk = false;
  isRegisterFail: boolean;
  isEnterprise: boolean;
  userId: number;

  countries: string[] = ["Andorra", "Austria", "Alemania", "España", "Francia", "Italia", "Suiza"]
  stations: Station[];
  classes: Classes[];
  skiMaterial: SkiMaterial[];
  carRental: CarRental[];
  hotel: Hotel[];
  hasServiceSkiMaterial = false;
  hasServiceHotel = false;
  hasServiceClasses = false;
  hasServiceCarRental = false;

  username: string;

  constructor(private formBuilder: FormBuilder, private enterpriseService: EnterpriseService, private adminService: AdminService
    , private classesService: ClassesService, private hotelService: HotelService, private skiMaterialService: SkiMaterialService, private carRentalService: CarRentalService,
    private toastr: ToastrService, private stationService: StationService
    , public dialogo: MatDialog) { }



  ngOnInit(): void {

    this.getStations();
    this.listAllServices();
    this.isEnterpriseCheck();

    this.enterpriseForm = this.formBuilder.group({
      nomComercial: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
      nif: ['', [Validators.required, Validators.pattern("[0-9]{8}[A-Za-z]{1}")]],
      cnae: ['', [Validators.required, Validators.pattern('[A-Z]{1}[0-9]{3}')]],
      activity: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
      location: ['', [Validators.required]],
      enterprisePhone: ['', [Validators.required, Validators.pattern('[0-9]{9}')]],
      enterpriseEmail: ['', [Validators.required, Validators.email]]
    });
  }


  get f() { return this.enterpriseForm.controls; }



  onSubmit() {
    this.submitted = true;

    if (this.enterpriseForm.invalid) {
      return;
    }

    this.dialogo.open(ConfirmDialogComponent, {
      data: `¿Estás seguro de esta acción?`
    })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          let nomComercial = this.enterpriseForm.get('nomComercial').value;
          let nif = this.enterpriseForm.get('nif').value;
          let cnae = this.enterpriseForm.get('cnae').value;
          let activity = this.enterpriseForm.get('activity').value;
          let location = this.enterpriseForm.get('location').value;
          let enterprisePhone = this.enterpriseForm.get('enterprisePhone').value;
          let enterpriseEmail = this.enterpriseForm.get('enterpriseEmail').value;


          this.enterprise = new Enterprise(nomComercial, nif, cnae, activity, location, enterprisePhone, enterpriseEmail);

          this.username = sessionStorage.getItem('AuthUsername');

          this.enterpriseService.getIdUsername(this.username).subscribe(
            data => {
              this.userId = data.id

              if (data.isEnterprise == 2 || data.isEnterprise == 1) {

                this.toastr.warning('Ya has registrado una empresa o está en proceso de admisión', 'Registro', {
                  timeOut: 3000,
                });
                return
              }

              this.enterpriseService.putUserDetailsToEnterprise(this.enterprise, this.userId).subscribe(
                data => {
                  this.enterprise = data;
                  this.isRegisterFail = true;
                  this.toastr.success('Solicitud de registro realizada', 'Registro', {
                    timeOut: 3000,
                  });
                  window.location.reload();
                }, err => {
                  this.isRegisterFail = false;
                  this.toastr.error('Ha habido algún fallo', 'Registro', {
                    timeOut: 3000,
                  });
                }
              );
            }
          );

          setTimeout(() => {
            this.submitted = false;
          }, 3500);
        }
      })


  }

  isEnterpriseCheck() {
    this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
      data => {

        if (data.isEnterprise == 2) {
          this.enterpriseStatus = "Pendiente";
          this.inactiveEnterprise = true;

        } else if (data.isEnterprise == 1) {
          this.enterpriseStatus = "Registrado";
          this.isEnterpriseChk = true;
        } else {
          this.enterpriseStatus = "No solicitado";
        }

      }
    );

  }

  deleteEnterprise() {

    this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.adminService.putEnterpriseStatus(data.id, 2).subscribe();
        window.location.reload();
      }
    );

  }


  deleteEnterpriseFromSystem() {
    this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.adminService.putEnterpriseStatus(data.id, 0).subscribe();
        window.location.reload();
      }
    );
  }

  listClassesUser(userId: number) {
    this.classesService.getClassesListByUser(userId).subscribe(
      data => {
        this.classes = data._embedded.classes;

        if (this.classes.length != 0) {
          this.hasServiceClasses = true;
        }

      }
    );

  }

  listHotelUser(userId: number) {
    this.hotelService.getHotelListByUser(userId).subscribe(
      data => {
        this.hotel = data._embedded.hotel;
        if (this.hotel.length != 0) {
          this.hasServiceHotel = true;
        }
      }
    );
  }

  listSkiMaterialUser(userId: number) {
    this.skiMaterialService.getSkiMaterialListByUser(userId).subscribe(
      data => {
        this.skiMaterial = data._embedded.skiMaterial;

        if (this.skiMaterial.length != 0) {
          this.hasServiceSkiMaterial = true;
        }

      }
    );

  }

  listCarRentalUser(userId: number) {
    this.carRentalService.getCarRentalListByUser(userId).subscribe(
      data => {
        this.carRental = data._embedded.carRental;

        if (this.carRental.length != 0) {
          this.hasServiceCarRental = true;
        }
      }
    );
  }

  listAllServices() {
    this.username = sessionStorage.getItem('AuthUsername');

    this.enterpriseService.getIdUsername(this.username).subscribe(
      data => {
        this.userId = data.id;

        this.listClassesUser(this.userId);
        this.listHotelUser(this.userId);
        this.listSkiMaterialUser(this.userId);
        this.listCarRentalUser(this.userId);

      }
    );
  }

  getStations() {
    this.stationService.stationList().subscribe(data => {
      this.stations = data;
    })
  }
}

