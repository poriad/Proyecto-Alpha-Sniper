import { Component, OnInit } from '@angular/core';
import { CarRental } from 'src/app/models/car-rental';
import { Classes } from 'src/app/models/classes';
import { Comentario } from 'src/app/models/comment';
import { Hotel } from 'src/app/models/hotel';
import { SkiMaterial } from 'src/app/models/ski-material';
import { CarRentalService } from 'src/app/service/car-rental.service';
import { ClassesService } from 'src/app/service/classes.service';
import { CommentService } from 'src/app/service/comment.service';
import { EnterpriseService } from 'src/app/service/enterprise.service';
import { HotelService } from 'src/app/service/hotel.service';
import { SkiMaterialService } from 'src/app/service/ski-material.service';

@Component({
  selector: 'app-enterprise-comments',
  templateUrl: './enterprise-comments.component.html',
  styleUrls: ['./enterprise-comments.component.css']
})
export class EnterpriseCommentsComponent implements OnInit {

  classes: Classes[];
  hotel: Hotel[];
  carRental: CarRental[];
  skiMaterial: SkiMaterial[];
  comments: Comentario[]=[];
  commentsAux: Comentario[];
  userId:number;
  searchText;
  
  constructor(private classesService: ClassesService, private commentService: CommentService,
     private enterpriseService: EnterpriseService, private hotelService: HotelService, private skiMaterialService: SkiMaterialService, private carRentalService: CarRentalService) { }

  ngOnInit(): void {

    this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.userId = data.id;

        this.listClassesWithComments(this.userId);
        this.listHotelWithComments(this.userId);
        this.listSkiMaterialWithComments(this.userId);
        this.listCarRentalWithComments(this.userId);

      }
    );
  }


  listClassesWithComments(userId: number){

    this.classesService.getClassesListByUser(userId).subscribe(
      data => {
        this.classes = data._embedded.classes;

        this.classes.forEach(element => {
          
          this.commentService.getCommentByClasssId(element.id).subscribe(
            dataDos => {
              this.commentsAux = dataDos._embedded.comment;
              this.commentsAux.forEach(element => {

                this.comments.push(element);
              });
              
            }
          )
          
          
        });

      });

      
  }

  listHotelWithComments(userId: number){

    this.hotelService.getHotelListByUser(userId).subscribe(
      data => {
        this.hotel = data._embedded.hotel;

        this.hotel.forEach(element => {
          
          this.commentService.getCommentByHotelId(element.id).subscribe(
            dataDos => {
              this.commentsAux = dataDos._embedded.comment;
              this.commentsAux.forEach(element => {

                this.comments.push(element);
              });
              
            }
          )
          
          
        });

      });
  }

  listSkiMaterialWithComments(userId: number){

    this.skiMaterialService.getSkiMaterialListByUser(userId).subscribe(
      data => {
        this.skiMaterial = data._embedded.skiMaterial;

        this.skiMaterial.forEach(element => {
          
          this.commentService.getCommentBySkiMaterialId(element.id).subscribe(
            dataDos => {
              this.commentsAux = dataDos._embedded.comment;
              this.commentsAux.forEach(element => {

                this.comments.push(element);
              });
              
            }
          )
          
          
        });

      });
  }

  listCarRentalWithComments(userId: number){

    this.carRentalService.getCarRentalListByUser(userId).subscribe(
      data => {
        this.carRental = data._embedded.carRental;

        this.carRental.forEach(element => {
          
          this.commentService.getCommentByCarRentalId(element.id).subscribe(
            dataDos => {
              this.commentsAux = dataDos._embedded.comment;
              this.commentsAux.forEach(element => {

                this.comments.push(element);

              });
              
            }
          )
          
          
        });

      });
  }

}

