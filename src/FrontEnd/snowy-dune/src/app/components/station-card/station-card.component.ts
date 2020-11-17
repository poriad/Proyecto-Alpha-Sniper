import { Station } from 'src/app/models/station';
import { StationService } from './../../service/station.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-station-card',
  templateUrl: './station-card.component.html',
  styleUrls: ['./station-card.component.css']
})
export class StationCardComponent implements OnInit {

  stations: Station[] = [];
  currentCountry: string = 'España';
  previouStationCountry: string = 'España';
  searchMode: boolean = false;

  //Pagination
  thePageNumber: number = 1;
  thePageSize:number = 10;
  theTotalElements: number = 0;
  
  previousKeyword: string = null

  constructor(
    private stationService: StationService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.loadStation();
  }

  handleSearchProducts() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword');

    if(this.previousKeyword != theKeyword){
      this.thePageNumber = 1;
    }

    this.previousKeyword = theKeyword;

    this.stationService.searchStationsPaginate(this.thePageNumber -1,
      this.thePageSize,theKeyword).subscribe(this.processResult());
  }

  loadStation(): void {

    if(this.previouStationCountry != this.currentCountry){
      this.thePageNumber = 1;
    }

    this.previouStationCountry = this.currentCountry;

    console.log(`currentCountry=${this.currentCountry}, thePageNumber=${this.thePageNumber}`)

    this.stationService.getStationListPaginate(this.thePageNumber - 1,
      this.thePageSize,
      this.currentCountry).subscribe(this.processResult());
    /*
    this.stationService.stationList().subscribe(
      data => {
        this.stations = data;
      },
      err => {
        console.log(err)
      }
    );*/
  }

  processResult(){
    return data => {
      this.stations = data._embedded.stations;
      this.thePageNumber = data.page.number +1;
      this.thePageSize = data.page.size;
      this.theTotalElements = data.page.totalElements
    };
  }

  updatePageSize(pageSize: number){
    this.thePageSize = pageSize;
    this.thePageNumber = 1;
    this.loadStation();
  }
}
