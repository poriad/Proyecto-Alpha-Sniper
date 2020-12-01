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

@Component({
  selector: 'app-modal-user-comments',
  templateUrl: './modal-user-comments.component.html',
  styleUrls: ['./modal-user-comments.component.css']
})
export class ModalUserCommentsComponent implements OnInit {

  tripsDone: Trip[] = [];
  stations: Station[] = [];
  hotel: Hotel[]  = [];
  skiMaterial: SkiMaterial[] = [];
  carRental: CarRental[] = [];
  classes: Classes[] = [];
  userId: number;
  commentId: number;

  constructor(private tripService: TripService,
    private enterpriseService: EnterpriseService,
    private stationService: StationService,
    private commentService: CommentService,
    public dialogo: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public mensaje: string) { }

    cerrarDialogo(): void {
      this.dialogo.close(false);
    }

  ngOnInit() {

    this.getCommentsForServices();
  }

  createComment(id: number, type: number) {

    let comment = <ComentarioDto>{
      comment: "hola"
    }
    this.commentService.newComment(comment).subscribe(
      data => {
        this.commentId = data.id;

      }, err => {

      }, () => {

        switch (+type) {
          case 1:
            this.commentService.putCommentByStationUserId(id, this.userId,this.commentId).subscribe(
              data => {

              }, err => {
                console.log(err)
              }
            )
            break;
          case 2:
            
            break;
          case 3:
            
            break;

          case 4:
            
            break;
          case 5:
            
            break;
          default:

            break;
        }

      }
    );

  }

  getCommentsForServices(){

    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {

        this.userId = data.id;

        this.tripService.getTripsDoneLastMonth(this.userId).subscribe(
          data => {
            this.tripsDone = data._embedded.trip;
            console.log(this.tripsDone);
          },err=> {

          }, () => {

            this.tripsDone.forEach(element => {

              this.tripService.getStationTrip(element.id).subscribe(
                data => {
                  if (data != undefined){
                    this.stations.push(data);
                  }
                  
                }
              )

              this.tripService.getHotelTrip(element.id).subscribe(
                data => {
                  if (data != undefined){
                  this.hotel.push(data);
                  }
                }
              )

              this.tripService.getSkiMaterialTrip(element.id).subscribe(
                data => {
                  if (data != undefined){
                  this.skiMaterial.push(data);
                  }
                }
              )

              this.tripService.getClassesTrip(element.id).subscribe(
                data => {
                  if (data != undefined){
                  this.classes.push(data);
                  }
                }
              )

              this.tripService.getCarRentalTrip(element.id).subscribe(
                data => {
                  if (data != undefined){
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