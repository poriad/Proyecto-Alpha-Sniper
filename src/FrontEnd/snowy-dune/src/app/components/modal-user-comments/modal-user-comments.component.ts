import { EnterpriseService } from './../../service/enterprise.service';
import { TripService } from './../../service/trip.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Trip } from 'src/app/models/trip';

@Component({
  selector: 'app-modal-user-comments',
  templateUrl: './modal-user-comments.component.html',
  styleUrls: ['./modal-user-comments.component.css']
})
export class ModalUserCommentsComponent implements OnInit {

  tripsDone: Trip[] = [];
  userId: number;

  constructor(private tripService: TripService,
    private enterpriseService: EnterpriseService,
    public dialogo: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public mensaje: string) { }

    cerrarDialogo(): void {
      this.dialogo.close(false);
    }

  ngOnInit() {

    this.getCommentsForServices();
  }

  getCommentsForServices(){

    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {

        this.userId = data.id;

        this.tripService.getTripsDoneLastMonth(this.userId).subscribe(
          data => {
            this.tripsDone = data._embedded.trip;
            console.log(this.tripsDone);
          }, () => console.log("observable complete")
          
        )


      }
    )

    
  }


}
