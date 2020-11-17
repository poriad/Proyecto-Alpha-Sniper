import { LoginUser } from '../../models/login-user';
import { AuthService } from '../../service/auth.service';
import { TokenService } from '../../service/token.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLogged = false;
  isLoginFail = false;
  loginUser: LoginUser;
  userName: string;
  password: string;
  roles: string[] = [];
  errorMsj: string;

  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {

    if (this.tokenService.getToken()){
      this.isLogged = true;
      this.isLoginFail = false;
      this.roles = this.tokenService.getAuthorities();
    }
  }

  public onLogOut(){
    this.tokenService.logOut();
    window.location.reload();
  }

  public onLogin(): void {
    this.loginUser = new LoginUser(this.userName, this.password);
    console.log(this.userName, this.password);
    this.authService.login(this.loginUser).subscribe(
      data => {
        this.isLogged = true;
        this.isLoginFail = false;

        this.tokenService.setToken(data.token);
        this.tokenService.setUsername(data.userName);
        this.tokenService.setAuthorities(data.authorities);
        this.roles = data.authorities;

        this.router.navigate(['/HomeUser'])
      },
      err => {
        this.isLogged = false;
        this.isLoginFail = true;
        this.errorMsj = err.error.message;
        //console.log(this.errorMsj)
      }
    )
  }

}
