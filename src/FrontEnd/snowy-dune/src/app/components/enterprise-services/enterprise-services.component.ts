import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { data } from 'jquery';
import { Hotel } from 'src/app/models/hotel';
import { Station } from 'src/app/models/station';
import { CarRentalService } from 'src/app/service/car-rental.service';
import { ClassesService } from 'src/app/service/classes.service';
import { EnterpriseService } from 'src/app/service/enterprise.service';
import { HotelService } from 'src/app/service/hotel.service';
import { SkiMaterialService } from 'src/app/service/ski-material.service';
import { StationService } from 'src/app/service/station.service';

@Component({
  selector: 'app-enterprise-services',
  templateUrl: './enterprise-services.component.html',
  styleUrls: ['./enterprise-services.component.css']
})
export class EnterpriseServicesComponent implements OnInit {

  enterpriseChk = false;
  stations: Station[];
  typeServices: string[] = ["Material de Ski", "Hotel", "Alquiler de vehículos", "Clases de ski"];
  serviceForm: FormGroup;
  submitted = false;
  Message: string;
  isRegisterFail: boolean;
  isEnterprise: boolean;
  userId: number;
  
  constructor(private formBuilder: FormBuilder,
    private enterpriseService: EnterpriseService,
    private stationService: StationService, 
    private hotelService: HotelService,
    private classesService: ClassesService,
    private carRentalService: CarRentalService,
    private skiMaterialService: SkiMaterialService) { }

  ngOnInit(): void {

    this.getStations();

    this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        if (data.isEnterprise == 1){
          this.enterpriseChk = true;
        }
      }
    );

    this.serviceForm = this.formBuilder.group({
      price: ['', Validators.required],
      name: ['', Validators.required],
      location: ['', Validators.required],
      country: ['', Validators.required],
      phone: ['', [Validators.required]],
      email: ['', [Validators.required]],
      urlImages: ['', Validators.required],
      description:['',Validators.required],
      typeService:['', Validators.required]
    });

  }

  get f() { return this.serviceForm.controls; }

  onSubmit() {
    
    this.submitted = true;

    if (this.serviceForm.invalid) {
      return;
    }

    let name = this.serviceForm.get('name').value;
    let description = this.serviceForm.get('description').value;
    let price = this.serviceForm.get('price').value;
    let stationId = this.serviceForm.get('location').value;
    let country = this.serviceForm.get('country').value;
    let phone = this.serviceForm.get('phone').value;
    let email = this.serviceForm.get('email').value;
    let urlImages = this.serviceForm.get('urlImages').value;
    let typeService = this.serviceForm.get('typeService').value;

    let location: string;

    this.stations.forEach(station => {
      if(station.id == stationId){
        location = station.name;
      }
    });

     
    //Hacer funcion
    switch(typeService){
      case 'Hotel': {
        let hotel = <HotelDto> {
          price: price,
          description: description,
          name: name,
          phone: phone,
          email: email,
          location: location,
          country: country,
          urlImages : urlImages,
          activated: 0,
          stars: 4,
        };
        let hotelId;

        this.hotelService.newHotel(hotel).subscribe(
          data => {
            hotelId = data.id
          
            this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
              data => {
               this.userId = data.id
               this.hotelService.putHotelUserId(hotelId,this.userId).subscribe();

                this.hotelService.putHotelStationId(hotelId, stationId).subscribe();
              }
            );
            
          }, err => {
            console.log(err);
          }
        )
        
        break;
      }

      case 'Material de Ski': {
        let skiMaterial = <SkiMaterialDto> {
          priceDay: price,
          description: description,
          name: name,
          phone: phone,
          email: email,
          location: location,
          country: country,
          urlImages : urlImages,
          activated: 0
        };

        let skiMaterialId;

        this.skiMaterialService.newSkiMaterial(skiMaterial).subscribe(
          data => {
            skiMaterialId = data.id
          
            this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
              data => {
               this.userId = data.id
               this.skiMaterialService.putSkiMaterialUserId(skiMaterialId,this.userId).subscribe();

                this.skiMaterialService.putSkiMaterialStationId(skiMaterialId, stationId).subscribe();
              }
            );
            
          }, err => {
            console.log(err);
          }
        )

        break;
        
      }
      case 'Alquiler de vehículos': {

        let carRental = <CarRentalDto> {
          price: price,
          description: description,
          name: name,
          phone: phone,
          email: email,
          location: location,
          country: country,
          urlImages : urlImages,
          activated: 0
        };

        let carRentalId;

        this.carRentalService.newCarRental(carRental).subscribe(
          data => {
            carRentalId = data.id
          
            this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
              data => {
               this.userId = data.id
               this.carRentalService.putCarRentalUserId(carRentalId,this.userId).subscribe();

                this.carRentalService.putCarRentalStationId(carRentalId, stationId).subscribe();
              }
            );
            
          }, err => {
            console.log(err);
          }
        )

        break;
      }
      case 'Clases de ski': {
        
        let classes = <ClassesDto> {
          priceHour: price,
          description: description,
          name: name,
          phone: phone,
          email: email,
          location: location,
          country: country,
          urlImages : urlImages,
          activated: 0
        };

        let classesId;

        this.classesService.newClasses(classes).subscribe(
          data => {
            classesId = data.id
          
            this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
              data => {
               this.userId = data.id
               this.classesService.putClassesUserId(classesId,this.userId).subscribe();

                this.classesService.putClassesStationId(classesId, stationId).subscribe();
              }
            );
            
          }, err => {
            console.log(err);
          }
        )

        break;
      }
    }

  }

  getStations(){
    this.stationService.stationList().subscribe(data => {
      this.stations = data;
    })
  }

}

export interface HotelDto {
  price: number;
  description: string;
  name: string;
  phone: string;
  email: string;
  location: string;
  country: string;
  urlImages: string;
  activated: number;
  stars: number;
}

export interface SkiMaterialDto {
  priceDay: number;
  description: string;
  name: string;
  phone: string;
  email: string;
  location: string;
  country: string;
  urlImages: string;
  activated: number;
}

export interface ClassesDto {
  priceHour: number;
  description: string;
  name: string;
  phone: string;
  email: string;
  location: string;
  country: string;
  urlImages: string;
  activated: number;
}

export interface CarRentalDto {
  price: number;
  description: string;
  name: string;
  phone: string;
  email: string;
  location: string;
  country: string;
  urlImages: string;
  activated: number;
}