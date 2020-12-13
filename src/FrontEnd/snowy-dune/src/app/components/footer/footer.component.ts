import { Component, OnInit } from '@angular/core';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  stateCtrl = {
    checked: true,
    
  };
  constructor() { }

  ngOnInit(): void {
  }

  onChange($event: MatSlideToggleChange) {
    console.log($event);
}
  
}
