import { Component, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { SkiMaterial } from 'src/app/models/ski-material';
import { SkiMaterialService } from 'src/app/service/ski-material.service';
import { Header } from 'src/app/utils/header';

@Component({
  selector: 'app-admin-ski-material',
  templateUrl: './admin-ski-material.component.html',
  styleUrls: ['./admin-ski-material.component.css']
})
export class AdminSkiMaterialComponent implements OnInit {
  @ViewChildren('closebutton') closebutton;

  header: Header[] = [{title:"Nombre",id:"name"},{title:"Localizacion",id:"location"},{title:"Teléfono",id:"phone"}];

  skiMaterials: SkiMaterial[] = [];
  skiMaterial: SkiMaterial;
  
  searchText;
  updateMessage: string = "";
  deleteMessage: string = "";
  isUpdateFail: boolean;
  isDeleteFail: boolean;
  submitted: boolean = false;
  submittedDelete: boolean = false;

  thePageNumber: number = 1;
  thePageSize:number = 8;
  theTotalElements: number = 0;
  columnName:string = "";
  order:string = "asc";

  constructor(private skiMaterialService:SkiMaterialService) { }

  ngOnInit(): void {
    this.orderSkiMaterialList();
  }


  updateStatusSkiMaterial(id): void{

    this.submitted = true;

    this.skiMaterialService.putSkiMaterialToActive(id).subscribe(
      data => {
        this.isUpdateFail = true;
        this.updateMessage = "Servicio activado";
        this.iterateChildrenButton();
        this.orderSkiMaterialList();
      }, err => {
        this.isUpdateFail = false;
        this.iterateChildrenButton();
        this.updateMessage = "El servicio no se ha podido actualizar";
      });

      setTimeout( () => { 
        this.submitted = false;
        this.updateMessage = "";
       },3000);
  }

  deleteSkiMaterial(id): void {

    this.submittedDelete = true;

    this.skiMaterialService.deleteSkiMaterial(id).subscribe(
      data => {
        this.isDeleteFail = true;
        this.deleteMessage = "Servicio pasado a Inactivo";
        this.iterateChildrenButton();
        this.orderSkiMaterialList();
      }, err => {
        this.isDeleteFail = false;
        this.iterateChildrenButton();
        this.deleteMessage = "El servicio no se ha podido actualizar";
      });

      setTimeout( () => { 
        this.submittedDelete = false;
        this.deleteMessage = "";
       },3000);
  }

  updateOrderListSkiMaterial(columnName:string){

    if (this.columnName == columnName){
      this.toggleOrder();
    } 
   
    this.columnName = columnName;
    
    console.log(this.columnName);
    //this.thePageNumber = 1;
    this.orderSkiMaterialList();
  }
  orderSkiMaterialList(){
    this.skiMaterialService.getSkiMaterialListPaginatedSorted(this.thePageNumber - 1,
      this.thePageSize,this.columnName,this.order).subscribe(this.processResult());
  }

  toggleOrder(){
    if (this.order == "desc"){
      this.order = "asc";
    } else {
      this.order = "desc";
    }
  }

  processResult(){
    return data => {
      this.skiMaterials = data._embedded.skiMaterial;
      this.thePageNumber = data.page.number +1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements
    };
  }

  iterateChildrenButton(){
    this.closebutton.forEach(element => {
      element.nativeElement.click();
    });
  }

}
