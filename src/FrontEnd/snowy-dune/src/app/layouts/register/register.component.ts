import { trigger, transition, style, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { NewUser } from 'src/app/models/new-user';
import { AuthService } from 'src/app/service/auth.service';
import { TokenService } from 'src/app/service/token.service';


// Validator
import { MustMatch } from 'src/app/utils/validador';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  animations: [
    trigger('fade', [
      transition('void => *', [
        style({ opacity: 0 }),
        animate(1000, style({ opacity: 1 }))
      ]),
      transition('* => void', [
        animate(1000, style({ opacity: 0 }))
      ])
    ])
  ]
})
export class RegisterComponent implements OnInit {

  isLogged = false;
  isRegisterFail: boolean;
  registerForm: FormGroup;
  newUser: NewUser;
  submitted = false;

  constructor(private tokenService: TokenService,
    private formBuilder: FormBuilder,
    private authService: AuthService, private toastr: ToastrService, public dialogo: MatDialog) { }

  ngOnInit(): void {

    if (this.tokenService.getToken()) {
      this.isLogged = true;
    }

    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      userName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
      acceptTerms: [false, Validators.requiredTrue],
      address: [''],
      phone: ['', [Validators.minLength(9), Validators.maxLength(9)]],
      newsletter: [false],
    }, {
      validator: MustMatch('password', 'confirmPassword')
    });

  }

  // convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }

  public onLogOut() {
    this.tokenService.logOut();
    window.location.reload();
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }

    this.dialogo.open(ConfirmDialogComponent, {
      data: `¿Estás seguro de realizar el registro?`
    })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          let firstName = this.registerForm.get('firstName').value;
          let lastName = this.registerForm.get('lastName').value;
          let email = this.registerForm.get('email').value;
          let userName = this.registerForm.get('userName').value;
          let password = this.registerForm.get('password').value;
          let address = this.registerForm.get('address').value;
          let newsletter = this.registerForm.get('newsletter').value;
          let phone = this.registerForm.get('phone').value;

          this.newUser = new NewUser(firstName, lastName, userName, email, password, address, newsletter, 0, phone);

          console.log(this.newUser);
          // display form values on success

          this.authService.new(this.newUser).subscribe(
            data => {
              this.isRegisterFail = true;
              this.newUser = data;
              this.onReset();
              this.toastr.success('Registro realizado con éxito', 'Registro', {
                timeOut: 3000,
              });
            }, err => {
              this.isRegisterFail = false;
              this.toastr.error('El nombre de usuario o el email ya se encuentra en uso', 'Registro', {
                timeOut: 3000,
              });
            }
          );
        }
      })

    this.submitted = false;

  }

  onReset() {
    this.submitted = false;
    this.registerForm.reset();
  }

}

