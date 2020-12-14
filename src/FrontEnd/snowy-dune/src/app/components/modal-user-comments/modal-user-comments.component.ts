import { EnterpriseService } from './../../service/enterprise.service';
import { TripService } from './../../service/trip.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Trip } from 'src/app/models/trip';
import { Station } from 'src/app/models/station';
import { Hotel } from 'src/app/models/hotel';
import { SkiMaterial } from 'src/app/models/ski-material';
import { CarRental } from 'src/app/models/car-rental';
import { Classes } from 'src/app/models/classes';
import { StationService } from 'src/app/service/station.service';
import { CommentService } from 'src/app/service/comment.service';
import { Comentario } from 'src/app/models/comment';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-modal-user-comments',
  templateUrl: './modal-user-comments.component.html',
  styleUrls: ['./modal-user-comments.component.css']
})
export class ModalUserCommentsComponent implements OnInit {

  tripsDone: Trip[] = [];
  stations: Station[] = [];
  hotel: Hotel[] = [];
  skiMaterial: SkiMaterial[] = [];
  carRental: CarRental[] = [];
  classes: Classes[] = [];
  userId: number;
  commentId: number;
  comment;
  submitted: boolean = false;

  stationForm: FormGroup;
  hotelForm: FormGroup;
  classesForm: FormGroup;
  skiMaterialForm: FormGroup;
  carRentalForm: FormGroup;

  constructor(private tripService: TripService,
    private enterpriseService: EnterpriseService,
    private stationService: StationService,
    private commentService: CommentService,
    private toastr: ToastrService,
    private formBuilder: FormBuilder,
    public dialogo: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public mensaje: string) { }

  cerrarDialogo(): void {
    this.dialogo.close(false);
  }

  ngOnInit() {

    this.stationForm = this.formBuilder.group({
      comment: ['', Validators.required]
    });

    this.hotelForm = this.formBuilder.group({
      commentHotel: ['', Validators.required]
    });

    this.classesForm = this.formBuilder.group({
      commentClasses: ['', Validators.required]
    });

    this.carRentalForm = this.formBuilder.group({
      commentCarRental: ['', Validators.required]
    });

    this.skiMaterialForm = this.formBuilder.group({
      commentSki: ['', Validators.required]
    });

    this.getCommentsForServices();
  }

  get fStation() { return this.stationForm.controls; }
  get fHotel() { return this.hotelForm.controls; }
  get fClasses() { return this.classesForm.controls; }
  get fCarRental() { return this.carRentalForm.controls; }
  get fSkiMaterial() { return this.skiMaterialForm.controls; }

  createComment(id: number, type: number) {
    this.submitted = true;

    switch (+type) {
      case 1:
        if (this.stationForm.invalid) {

          return;
        }

        this.comment = <ComentarioDto>{
          comment: this.stationForm.get('comment').value
        }
        break;
      case 2:
        if (this.hotelForm.invalid) {
          return;
        }

        this.comment = <ComentarioDto>{
          comment: this.hotelForm.get('comment').value
        }
        break;
      case 3:
        if (this.carRentalForm.invalid) {
          return;
        }

        this.comment = <ComentarioDto>{
          comment: this.carRentalForm.get('comment').value
        }
        break;
      case 4:
        if (this.classesForm.invalid) {
          return;
        }

        this.comment = <ComentarioDto>{
          comment: this.classesForm.get('comment').value
        }
        break;
      case 5:
        if (this.skiMaterialForm.invalid) {
          return;
        }

        this.comment = <ComentarioDto>{
          comment: this.skiMaterialForm.get('comment').value
        }
        break;
    }

    this.commentService.newComment(this.comment).subscribe(
      data => {
        this.commentId = data.id;

      }, err => { },
      () => {

        switch (+type) {
          case 1:
            this.commentService.putCommentByStationUserId(id, this.userId, this.commentId).subscribe(
              data => {
                this.toastr.success('Comentario Realizado. ¡Muchas Gracias!', 'Comentario', {
                  timeOut: 3000,
                });
              }, err => {
                this.toastr.error('¡Lo sentimos! El comentario no se ha registrado', 'Comentario', {
                  timeOut: 3000,
                });
              }
            )
            break;
          case 2:
            this.commentService.putCommentByHotelUserId(id, this.userId, this.commentId).subscribe(
              data => {
                this.toastr.success('Comentario Realizado. ¡Muchas Gracias!', 'Comentario', {
                  timeOut: 3000,
                });
              }, err => {
                this.toastr.error('¡Lo sentimos! El comentario no se ha registrado', 'Comentario', {
                  timeOut: 3000,
                });
              }
            )
            break;
          case 3:
            this.commentService.putCommentByCarRentalUserId(id, this.userId, this.commentId).subscribe(
              data => {
                this.toastr.success('Comentario Realizado. ¡Muchas Gracias!', 'Comentario', {
                  timeOut: 3000,
                });
              }, err => {
                this.toastr.error('¡Lo sentimos! El comentario no se ha registrado', 'Comentario', {
                  timeOut: 3000,
                });
              }
            )
            break;

          case 4:
            this.commentService.putCommentByClassesUserId(id, this.userId, this.commentId).subscribe(
              data => {
                this.toastr.success('Comentario Realizado. ¡Muchas Gracias!', 'Comentario', {
                  timeOut: 3000,
                });
              }, err => {
                this.toastr.error('¡Lo sentimos! El comentario no se ha registrado', 'Comentario', {
                  timeOut: 3000,
                });
              }
            )
            break;
          case 5:
            this.commentService.putCommentBySkiMaterialUserId(id, this.userId, this.commentId).subscribe(
              data => {
                this.toastr.success('Comentario Realizado. ¡Muchas Gracias!', 'Comentario', {
                  timeOut: 3000,
                });
              }, err => {
                this.toastr.error('¡Lo sentimos! El comentario no se ha registrado', 'Comentario', {
                  timeOut: 3000,
                });
              }
            )
            break;
        }

      }
    );

  }

  getCommentsForServices() {

    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {

        this.userId = data.id;

        this.tripService.getTripsDoneLastMonth(this.userId).subscribe(
          data => {
            this.tripsDone = data._embedded.trip;
            console.log(this.tripsDone);
          }, err => {

          }, () => {

            this.tripsDone.forEach(element => {

              this.tripService.getStationTrip(element.id).subscribe(
                data => {
                  if (data != undefined) {
                    this.stations.push(data);
                  }

                }
              )

              this.tripService.getHotelTrip(element.id).subscribe(
                data => {
                  if (data != undefined) {
                    this.hotel.push(data);
                  }
                }
              )

              this.tripService.getSkiMaterialTrip(element.id).subscribe(
                data => {
                  if (data != undefined) {
                    this.skiMaterial.push(data);
                  }
                }
              )

              this.tripService.getClassesTrip(element.id).subscribe(
                data => {
                  if (data != undefined) {
                    this.classes.push(data);
                  }
                }
              )

              this.tripService.getCarRentalTrip(element.id).subscribe(
                data => {
                  if (data != undefined) {
                    this.carRental.push(data);
                  }
                }
              )


            });
          }



        )
      }
    )


  }


}


export interface ComentarioDto {
  comment: string;

}