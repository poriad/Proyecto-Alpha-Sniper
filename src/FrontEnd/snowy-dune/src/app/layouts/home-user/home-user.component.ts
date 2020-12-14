import { TokenService } from 'src/app/service/token.service';
import { Component, OnInit } from '@angular/core';
import { trigger, transition, style, animate } from '@angular/animations';
import { MatDialog } from '@angular/material/dialog';
import { TutorialDialogComponent } from 'src/app/components/tutorial-dialog/tutorial-dialog.component';
import { AccessEnterpriseDialogComponent } from 'src/app/components/access-enterprise-dialog/access-enterprise-dialog.component';
import { EnterpriseService } from 'src/app/service/enterprise.service';

@Component({
  selector: 'app-home-user',
  templateUrl: './home-user.component.html',
  styleUrls: ['./home-user.component.css'],
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
export class HomeUserComponent implements OnInit {

  isLogged = false;
  username = '';
  userId: number;

  constructor(private tokenService: TokenService, public dialogo: MatDialog, private enterpriseService: EnterpriseService) { }

  ngOnInit(): void {

    this.enterpriseService.getIdUsername(this.tokenService.getUsername()).subscribe(
      data => {
        this.userId = data.id
      }
    )

    if (this.tokenService.getToken()) {
      this.isLogged = true;
      this.username = this.tokenService.getUsername();
    } else {
      this.isLogged = false;
      this.username = '';
    }
  }

  tutorial() {

    this.dialogo.open(TutorialDialogComponent)

  }



  accessEnterprise() {

    this.dialogo.open(AccessEnterpriseDialogComponent)
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          this.enterpriseService.putUserToEnterprise(this.userId).subscribe(
            data => {

            }
          )
        }
      })

  }
}
