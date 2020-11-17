import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenService } from '../service/token.service';

@Injectable({
  providedIn: 'root'
})
export class ProdGuardService implements CanActivate{

  realRol: String;

  constructor(
    private tokenService: TokenService,
    private router: Router
    ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const expectedRol = route.data.expectedRol;

    const roles = this.tokenService.getAuthorities();
    this.realRol = 'user';

    roles.forEach(rol => {
      if (rol === 'ROLE_ADMIN'){
        this.realRol = 'admin';
      }

      if (rol != 'ROLE_ADMIN' && this.realRol != 'admin' && rol !='ROLE_USER'){
        this.realRol = 'enterprise';
      }
    });

    if (!this.tokenService.getToken() || expectedRol.indexOf(this.realRol) === -1){
      this.router.navigate(["/Login"]);
      return false;
    }

    return true;

  }
}
