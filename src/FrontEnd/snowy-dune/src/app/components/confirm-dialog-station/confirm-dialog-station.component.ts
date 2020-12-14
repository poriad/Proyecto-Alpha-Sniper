import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-confirm-dialog-station',
  templateUrl: './confirm-dialog-station.component.html',
  styleUrls: ['./confirm-dialog-station.component.css']
})
export class ConfirmDialogStationComponent implements OnInit {

  constructor(
    public dialogo: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public mensaje: string) { }

  cerrarDialogo(): void {
    this.dialogo.close(false);
  }

  ngOnInit() {
  }
}
