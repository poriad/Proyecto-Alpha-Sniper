import { Component, Inject, OnInit, ViewEncapsulation } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-tutorial-dialog',
  templateUrl: './tutorial-dialog.component.html',
  styleUrls: ['./tutorial-dialog.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class TutorialDialogComponent implements OnInit {

  constructor(
    public dialogo: MatDialogRef<TutorialDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public mensaje: string) { }

  cerrarDialogo(): void {
    this.dialogo.close(false);
  }

  ngOnInit() {
  }

}
