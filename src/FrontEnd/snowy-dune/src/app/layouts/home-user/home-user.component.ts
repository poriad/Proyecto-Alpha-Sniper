import { TokenService } from 'src/app/service/token.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-user',
  templateUrl: './home-user.component.html',
  styleUrls: ['./home-user.component.css']
})
export class HomeUserComponent implements OnInit {

  isLogged = false;
  username = '';

  constructor(private tokenService: TokenService) { }

  ngOnInit(): void {
    if(this.tokenService.getToken()) {
      this.isLogged = true;
      this.username = this.tokenService.getUsername();
    } else {
      this.isLogged = false;
      this.username = '';
    }
  }

}
