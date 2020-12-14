import { Station } from 'src/app/models/station';
import { StationService } from './../../service/station.service';
import { Component, OnInit } from '@angular/core';
import { animate, style, transition, trigger } from '@angular/animations';
import { CommentService } from 'src/app/service/comment.service';
import { Comentario } from 'src/app/models/comment';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogStationComponent } from '../confirm-dialog-station/confirm-dialog-station.component';
import { Router } from '@angular/router';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { requiredDate } from 'src/app/utils/validador';


@Component({
  selector: 'app-station-card',
  templateUrl: './station-card.component.html',
  styleUrls: ['./station-card.component.css'],
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
export class StationCardComponent implements OnInit {
  itemsLength: number;

  tripForm: FormGroup;
  comments: Comentario[];
  user: string[] = [];
  searchText;
  stations: Station[] = [];
  countries: string[] = ["Andorra", "Austria", "Alemania", "España", "Francia", "Italia", "Suiza"];
  currentCountry: string = 'Andorra';
  previouStationCountry: string = 'Andorra';
  searchMode: boolean = false;
  numberPersons: number;
  entryDate: Date;
  numDias: number;
  submitted: boolean = false;

  //Pagination
  thePageNumber: number = 1;
  thePageSize: number = 8;
  theTotalElements: number = 0;


  constructor(
    private stationService: StationService, private commentService: CommentService, private formBuilder: FormBuilder, public dialogo: MatDialog, private router: Router
  ) { }

  ngOnInit(): void {
    this.loadStation();

    this.tripForm = this.formBuilder.group({
      personNumber: ['', [Validators.required, Validators.pattern("[0-9]{1}")]],
      numberDays: ['', [Validators.required, Validators.pattern("[1-9]{1}[0-9]{0,1}")]],
      entryDate: ['', [Validators.required]]
    }, {
      validator: requiredDate()
    });
  }


  get f() { return this.tripForm.controls; }

  loadStation(): void {

    if (this.previouStationCountry != this.currentCountry) {
      this.thePageNumber = 1;
    }

    this.previouStationCountry = this.currentCountry;

    console.log(`currentCountry=${this.currentCountry}, thePageNumber=${this.thePageNumber}`)

    this.stationService.getStationListPaginate(this.thePageNumber - 1,
      this.thePageSize,
      this.currentCountry).subscribe(this.processResult());

  }

  // Añadir validacion fecha posterior a hoy
  validateInfo(location: string, stationId) {

    this.submitted = true;

    if (this.tripForm.invalid) {

      this.dialogo.open(ConfirmDialogStationComponent, {
        data: `Antes de seguir, debes rellenar el Nº de personas, la fecha de entrada y el Nº de días.
        
        Recuerda que las fechas solo se pueden poner a futuro.
        `
      })

    } else {
      let personNumber = this.tripForm.get('personNumber').value;
      window.sessionStorage.setItem('PersonNumber', personNumber)

      let numberDays = this.tripForm.get('numberDays').value;
      window.sessionStorage.setItem('NumberDays', numberDays)

      let entryDate = this.tripForm.get('entryDate').value;
      window.sessionStorage.setItem('EntryDate', entryDate)

      window.sessionStorage.setItem('StationId', stationId)

      window.sessionStorage.setItem('Station', location)

      this.dialogo.open(ConfirmDialogComponent, {
        data: `¿Estás seguro de elegir la estación ${location}?`
      })
        .afterClosed()
        .subscribe((confirmado: Boolean) => {
          if (confirmado) {
            this.router.navigate(['/Hotel'])
          }
        })

    }

  }

  loadComments(id) {


    this.commentService.getCommentByStationIdPaginated(0, id).subscribe(
      data => {
        this.comments = data._embedded.comment;

        this.comments.forEach(element => {

          this.commentService.getCommentByUserId(element.id).subscribe(
            data => {
              this.user.push(data.username);
            }
          )

        });
      })
  }



  processResult() {
    return data => {
      this.stations = data._embedded.station;
      this.thePageNumber = data.page.number + 1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements;
    };
  }

  updatePageSize(pageSize: number) {
    this.thePageSize = pageSize;
    this.thePageNumber = 1;
    this.updateStationSelect(this.currentCountry);
  }

  updateStationSelect(country: string) {

    if (this.previouStationCountry != country) {
      this.thePageNumber = 1;
      this.currentCountry = country;
    }

    this.previouStationCountry = country;

    console.log(`currentCountry=${country}, thePageNumber=${this.thePageNumber}`)

    this.stationService.getStationListPaginate(this.thePageNumber - 1,
      this.thePageSize,
      country).subscribe(this.processResult());
  }
}
