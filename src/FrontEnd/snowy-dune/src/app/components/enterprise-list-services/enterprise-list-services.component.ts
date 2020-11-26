import { Component, OnInit } from '@angular/core';
import { CarRental } from 'src/app/models/car-rental';
import { Classes } from 'src/app/models/classes';
import { Hotel } from 'src/app/models/hotel';
import { SkiMaterial } from 'src/app/models/ski-material';
import { CarRentalService } from 'src/app/service/car-rental.service';
import { ClassesService } from 'src/app/service/classes.service';
import { EnterpriseService } from 'src/app/service/enterprise.service';
import { HotelService } from 'src/app/service/hotel.service';
import { SkiMaterialService } from 'src/app/service/ski-material.service';

@Component({
  selector: 'app-enterprise-list-services',
  templateUrl: './enterprise-list-services.component.html',
  styleUrls: ['./enterprise-list-services.component.css']
})
export class EnterpriseListServicesComponent implements OnInit {

  userId: number;

  classes: Classes[];
  skiMaterial: SkiMaterial[];
  carRental: CarRental[];
  hotel: Hotel[];

  constructor(private enterpriseService: EnterpriseService, private classesService: ClassesService, private hotelService: HotelService, private skiMaterialService: SkiMaterialService, private carRentalService: CarRentalService) { }

  ngOnInit(): void {

    this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.userId = data.id;

        this.listClassesUser(this.userId);
        this.listHotelUser(this.userId);
        this.listSkiMaterialUser(this.userId);
        this.listCarRentalUser(this.userId);

      }
    );

  }

  listClassesUser(userId: number){
    this.classesService.getClassesListByUser(userId).subscribe(
      data => {
        this.classes = data._embedded.classes;
      }
    );

  }

  listHotelUser(userId: number){
    this.hotelService.getHotelListByUser(userId).subscribe(
      data => {
        this.hotel = data._embedded.hotel;
      }
    );
  }

  listSkiMaterialUser(userId: number){
    this.skiMaterialService.getSkiMaterialListByUser(userId).subscribe(
      data => {
        this.skiMaterial = data._embedded.skiMaterial;
      }
    );

  }

  listCarRentalUser(userId: number){
    this.carRentalService.getCarRentalListByUser(userId).subscribe(
      data => {
        this.carRental = data._embedded.carRental;
      }
    );
  }
}
