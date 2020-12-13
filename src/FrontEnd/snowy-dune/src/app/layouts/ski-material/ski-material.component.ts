import { trigger, transition, style, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { Comentario } from 'src/app/models/comment';
import { SkiMaterial } from 'src/app/models/ski-material';
import { CommentService } from 'src/app/service/comment.service';
import { SkiMaterialService } from 'src/app/service/ski-material.service';

@Component({
  selector: 'app-ski-material',
  templateUrl: './ski-material.component.html',
  styleUrls: ['./ski-material.component.css'],
  animations: [
    trigger('fade', [      
      transition('void => *', [
        style({opacity: 0}),
        animate(1000, style({opacity: 1}))
      ]),
      transition('* => void', [
        animate(1000, style({opacity: 0}))
      ])
    ])
]
})

export class SkiMaterialComponent implements OnInit {

  skiMaterials: SkiMaterial[];
  comments: Comentario[];
  
  searchText;
  user: string[] = [];
  currentStars: number = 4;
  previouStationStars: number = 4;

  //Pagination
  thePageNumber: number = 1;
  thePageSize:number = 8;
  theTotalElements: number = 0;
  minPrice: number = 0;
  maxPrice: number = 9999;

  constructor(private skiMaterialService: SkiMaterialService, private router: Router, public dialogo: MatDialog, private commentService: CommentService) { }

  ngOnInit(): void {
    this.loadSkiMaterial();

  }

  loadSkiMaterial(): void {
    
    let country: string =  sessionStorage.getItem('Station');

    this.skiMaterialService.getSkiMaterialByCountryListPaginate(this.thePageNumber - 1,
      this.thePageSize,
      country).subscribe(this.processResult());
    
  }

  validateInfo(nombre,skiMaterialId){

    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro de elegir el material de ski en ${nombre}?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){
        window.sessionStorage.setItem('SkiMaterialId', skiMaterialId)
        this.router.navigate(['/CarRental']);
      }
    })
    
  }

  loadComments(id){
    
    this.commentService.getCommentBySkiMaterialIdPaginated(0,id).subscribe(
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

  updateSkiMaterialPriceSelect(price: number){

    var country: string =  sessionStorage.getItem('Station');
    

    switch (+price) {
      case 1: 
        this.minPrice = 0;
        this.maxPrice = 30;
        break;
      
      case 2: 
      this.minPrice  = 31;
      this.maxPrice = 60;
        break;
      
      case 3: 
      this.minPrice  = 61;
      this.maxPrice = 100;
        break;
      
      case 4: 
      this.minPrice  = 101;
      this.maxPrice = 200;
        break;
      
      case 5: 
      this.minPrice  = 201;
      this.maxPrice = 9999;
        break;
      default:{
        this.minPrice  = 0;
        this.maxPrice = 9999;
      }
      
    }

    this.skiMaterialService.getSkiMaterialByLocationPriceListPaginate(this.thePageNumber - 1,
      this.thePageSize,
      country,this.minPrice,this.maxPrice).subscribe(this.processResult());
   
  }

  backToClasses() {
    this.dialogo.open(ConfirmDialogComponent, {
      data:`¿Estás seguro que quieres volver?`
    })
    .afterClosed()
    .subscribe((confirmado:Boolean) => {
      if (confirmado){

        window.sessionStorage.removeItem('SkiMaterialId');
        this.router.navigate(['/Classes']);

      }
    })
  }

  continueToCarRental() {
    if (window.sessionStorage.getItem('SkiMaterialId') == undefined){
      this.dialogo.open(ConfirmDialogComponent, {
        data:`¿Estás seguro que quieres continuar? No has seleccionado ningun material`
      })
      .afterClosed()
      .subscribe((confirmado:Boolean) => {
        if (confirmado){
  
          window.sessionStorage.removeItem('SkiMaterialId');
          this.router.navigate(['/CarRental']);
  
        }
      })
    }
  }

  processResult(){
    return data => {
      this.skiMaterials = data._embedded.skiMaterial;
      this.thePageNumber = data.page.number +1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements;
    };
  }

}
