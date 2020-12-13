import { trigger, transition, style, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AdminService } from 'src/app/service/admin.service';
import { EnterpriseService } from 'src/app/service/enterprise.service';
import { TokenService } from 'src/app/service/token.service';
import { MustMatch } from 'src/app/utils/validador';

@Component({
  selector: 'app-confirm-password',
  templateUrl: './confirm-password.component.html',
  styleUrls: ['./confirm-password.component.css'],
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
export class ConfirmPasswordComponent implements OnInit {

  isLogged = false;
  isLoginFail = false;
  roles: string[] = [];
  isUserValid = false;
  submitted = false;
  submittedPassword = false;
  userForm: FormGroup;
  passwordForm: FormGroup;
  userId: number;
  
  constructor(private tokenService: TokenService,private formBuilder: FormBuilder, private enterpriseService: EnterpriseService, private adminService: AdminService,private toastr: ToastrService) { }

  ngOnInit(): void {
    //this.isUserValid = false;

    this.userForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', Validators.required],
      
  });

  this.passwordForm = this.formBuilder.group({
    password: ['', Validators.required],
    confirmPassword: ['', Validators.required],
    
}, {
  validator: MustMatch('password', 'confirmPassword')
});

    if (this.tokenService.getToken()){
      this.isLogged = true;
      this.isLoginFail = false;
      this.roles = this.tokenService.getAuthorities();
    }
  }

  get f() { return this.userForm.controls; }
  get fu() { return this.passwordForm.controls; }

  public onLogOut(){
    this.tokenService.logOut();
    window.location.reload();
  }


  verifyUser() {

    this.submitted = true;

    if (this.userForm.invalid) {
      this.toastr.error('Los datos no son válidos', 'Cambio Contraseña', {
        timeOut: 3000,
      });
      return;
  }

  let username = this.userForm.get('username').value;
  let email = this.userForm.get('email').value;

  this.enterpriseService.getIdUsername(username).subscribe(
    data => {
      if (data != null){
        this.userId= data.id;
        if (email == data.email){
          this.isUserValid = true;
          this.submitted = false;
          this.userForm.reset();
        }
      } else {
        this.toastr.error('Los datos no se corresponden o no son válidos', 'Cambio Contraseña', {
          timeOut: 3000,
        });
        this.submitted = false;
      }
      
    }
  )
  }

  updateUserPassword() {

    this.submittedPassword = true;

    if (this.passwordForm.invalid) {
      return;
  }

  let password = this.passwordForm.get('password').value;

    this.adminService.putUserPassword(this.userId, password).subscribe(
      (res) => {
        this.toastr.success('Se ha modificado la contraseña', 'Cambio Contraseña', {
          timeOut: 3000,
        });
        this.isUserValid = false;
        this.passwordForm.reset();
      }, 
      (err) => {
        this.toastr.error('Error en la modificación', 'Cambio Contraseña', {
          timeOut: 3000,
        });
      }
    )
  
  }

}


