import { trigger, transition, style, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-enterprise',
  templateUrl: './home-enterprise.component.html',
  styleUrls: ['./home-enterprise.component.css'],
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
export class HomeEnterpriseComponent implements OnInit {

  username: string;
  
  constructor() { }

  ngOnInit(): void {
    this.username = sessionStorage.getItem('AuthUsername');
  }

}
