import { TokenService } from 'src/app/service/token.service';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { TripService } from 'src/app/service/trip.service';
import { Trip } from 'src/app/models/trip';
import { EnterpriseService } from 'src/app/service/enterprise.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class NavigationComponent implements OnInit {


  isLogged = false;
  isAdmin = false;
  isEnterprise = false;
  roles: String[];
  trips: Trip[] =[];
  tripsAux: Trip[] = [];


  constructor(private tokenService: TokenService, private tripService: TripService, private enterpriseService: EnterpriseService,private router: Router) { }

  ngOnInit(): void {

    if (window.sessionStorage.getItem('AuthUsername') != null){
      
        this.getAllTripsInCart();
      
    }
    
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
    this.router.navigate(['login'])
  }

  getAllTripsInCart(){

    this.enterpriseService.getIdUsername(window.sessionStorage.getItem('AuthUsername')).subscribe(
      data => {

        this.tripService.getTripsInCartController(data.id).subscribe(
          data => {
            this.trips = data;
            
           
          }
        )

      }
    )

    

  }

}
