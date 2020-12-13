import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-access-enterprise-dialog',
  templateUrl: './access-enterprise-dialog.component.html',
  styleUrls: ['./access-enterprise-dialog.component.css']
})
export class AccessEnterpriseDialogComponent implements OnInit {

  constructor(
    public dialogo: MatDialogRef<AccessEnterpriseDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public mensaje: string) { }

    cerrarDialogo(): void {
      this.dialogo.close(false);
    }
    confirmado(): void {
      this.dialogo.close(true);
    }
    
  ngOnInit() {
  }
}
