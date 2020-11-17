import { TokenService } from 'src/app/service/token.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {


  isLogged = false;
  isAdmin = false;
  isEnterprise = false;
  roles: String[];

  constructor(private tokenService: TokenService) { }

  ngOnInit(): void {


    if(this.tokenService.getToken()){
      this.isLogged=true;
    } else {
      this.isLogged = false;
    }

    this.roles = this.tokenService.getAuthorities();

    this.roles.forEach(rol => {
      if (rol === 'ROLE_ADMIN'){
        this.isAdmin = true;
      } else if (rol === 'ROLE_ENTERPRISE') {
        this.isEnterprise = true;
      }
    });

  }

  onLogOut(): void{
    this.tokenService.logOut();
    window.location.reload();
  }

}
