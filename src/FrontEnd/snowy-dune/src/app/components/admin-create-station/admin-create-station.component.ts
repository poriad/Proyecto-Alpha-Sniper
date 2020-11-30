import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Forfait } from 'src/app/models/forfait';
import { Station } from 'src/app/models/station';
import { StationService } from 'src/app/service/station.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-admin-create-station',
  templateUrl: './admin-create-station.component.html',
  styleUrls: ['./admin-create-station.component.css']
})
export class AdminCreateStationComponent implements OnInit {

  countries: string[] = ["Andorra","Austria","Alemania","España","Francia","Italia","Suiza"]
  stations: Station[];

  stationForm: FormGroup;
  forfait: Forfait;
  station:Station;
  submitted = false;
  Message: string;
  isRegisterFail: boolean;

  constructor(private formBuilder: FormBuilder,private stationService: StationService,public dialogo: MatDialog) { }

  ngOnInit(): void {

    this.getStations();

    this.stationForm = this.formBuilder.group({
      name: ['', Validators.required],
      location: ['', Validators.required],
      country: ['', Validators.required],
      openingDate: ['', [Validators.required]],
      closingDate: ['', [Validators.required]],
      price: ['', Validators.required],
      description:['',[Validators.required,Validators.minLength(50)]]
    });
  }

  get f() { return this.stationForm.controls; }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.stationForm.invalid) {
      return;
    }


    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de esta acción?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        let name = this.stationForm.get('name').value;
        let location = this.stationForm.get('location').value;
        let country = this.stationForm.get('country').value;
        let openingDate = this.stationForm.get('openingDate').value;
        let closingDate = this.stationForm.get('closingDate').value;
        let description = this.stationForm.get('description').value;
        let price = this.stationForm.get('price').value;
    
        this.station = new Station(name,location,country,openingDate,closingDate,description,"",0,price);
    
        this.stationService.newStation(this.station).subscribe(
          data => {
            this.isRegisterFail = true;
            this.station = data;
            this.Message = "Estación registrada";
          },err => {
            this.isRegisterFail = false;
            this.Message = err.error.mensaje;;
            console.log(err);
          }
        );
    
        setTimeout( () => { 
          this.submitted = false;
          this.Message = "";
         },3000);
      }

      })
    
  }

  getStations() {
    this.stationService.stationList().subscribe((data) => {
      this.stations = data;
    });
  }



}
