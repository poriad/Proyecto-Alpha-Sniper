import { trigger, transition, style, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { Classes } from 'src/app/models/classes';
import { Comentario } from 'src/app/models/comment';
import { ClassesService } from 'src/app/service/classes.service';
import { CommentService } from 'src/app/service/comment.service';

@Component({
  selector: 'app-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.css'],
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
export class ClassesComponent implements OnInit {

  classes: Classes[];
  comments: Comentario[];

  searchText;
  user: string[] = [];
  minPrice: number = 0;
  maxPrice: number = 9999;

  //Pagination
  thePageNumber: number = 1;
  thePageSize: number = 8;
  theTotalElements: number = 0;

  constructor(private classesService: ClassesService, private router: Router, public dialogo: MatDialog, private commentService: CommentService) { }

  ngOnInit(): void {

    this.loadClasses();
  }

  loadClasses(): void {

    let country: string = sessionStorage.getItem('Station');

    this.classesService.getClassesByCountryListPaginate(this.thePageNumber - 1,
      this.thePageSize,
      country).subscribe(this.processResult());

  }

  validateInfo(nombre, classesId) {

    this.dialogo.open(ConfirmDialogComponent, {
      data: `¿Estás seguro de elegir las clases en ${nombre}?`
    })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          window.sessionStorage.setItem('ClassesId', classesId)
          this.router.navigate(['/SkiMaterial']);
        }
      })

  }

  loadComments(id) {

    this.commentService.getCommentByClassIdPaginated(0, id).subscribe(
      data => {
        this.comments = data._embedded.comment;

        this.comments.forEach(element => {

          this.commentService.getCommentByUserId(element.id).subscribe(
            data => {
              this.user.push(data.username);

            }
          )

        });
      }
    )
  }

  updateClassesPriceSelect(price: number) {

    var country: string = sessionStorage.getItem('Station');


    switch (+price) {
      case 1:
        this.minPrice = 0;
        this.maxPrice = 30;
        break;

      case 2:
        this.minPrice = 31;
        this.maxPrice = 60;
        break;

      case 3:
        this.minPrice = 61;
        this.maxPrice = 100;
        break;

      case 4:
        this.minPrice = 101;
        this.maxPrice = 200;
        break;

      case 5:
        this.minPrice = 201;
        this.maxPrice = 9999;
        break;
      default: {
        this.minPrice = 0;
        this.maxPrice = 9999;
      }
    }

    this.classesService.getClassesByLocationPriceListPaginate(this.thePageNumber - 1,
      this.thePageSize,
      country, this.minPrice, this.maxPrice).subscribe(this.processResult());


  }

  backToHotel() {
    this.dialogo.open(ConfirmDialogComponent, {
      data: `¿Estás seguro que quieres volver?`
    })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {

          window.sessionStorage.removeItem('ClassesId');
          this.router.navigate(['/Hotel']);

        }
      })
  }

  continueToSkiMaterial() {
    if (window.sessionStorage.getItem('ClassesId') == undefined) {
      this.dialogo.open(ConfirmDialogComponent, {
        data: `¿Estás seguro que quieres continuar? No has seleccionado ninguna clase`
      })
        .afterClosed()
        .subscribe((confirmado: Boolean) => {
          if (confirmado) {

            window.sessionStorage.removeItem('ClassesId');
            this.router.navigate(['/SkiMaterial']);

          }
        })
    }
  }

  processResult() {
    return data => {
      this.classes = data._embedded.classes;
      this.thePageNumber = data.page.number + 1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements;
    };
  }

}
