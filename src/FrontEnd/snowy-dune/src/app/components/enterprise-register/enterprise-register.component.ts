import { AdminService } from 'src/app/service/admin.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Enterprise } from 'src/app/models/enterprise';
import { NewUser } from 'src/app/models/new-user';
import { EnterpriseService } from 'src/app/service/enterprise.service';
import { TokenService } from 'src/app/service/token.service';

@Component({
  selector: 'app-enterprise-register',
  templateUrl: './enterprise-register.component.html',
  styleUrls: ['./enterprise-register.component.css']
})
export class EnterpriseRegisterComponent implements OnInit {

  enterpriseForm: FormGroup;
  enterprise: Enterprise;
  enterpriseStatus: string;
  inactiveEnterprise: boolean;
  submitted = false;
  Message: string;
  isEnterpriseChk = false;
  isRegisterFail: boolean;
  isEnterprise: boolean;
  userId: number;

  username: string;

  constructor(private formBuilder: FormBuilder, private enterpriseService: EnterpriseService, private adminService: AdminService) { }

  ngOnInit(): void {

    this.isEnterpriseCheck();

    this.enterpriseForm = this.formBuilder.group({
      nomComercial: ['', Validators.required],
      nif: ['', Validators.required],
      cnae: ['', Validators.required],
      activity: ['', [Validators.required]],
      location: ['', [Validators.required]],
      enterprisePhone: ['', Validators.required],
      enterpriseEmail:['',[Validators.required]]
    });
  }

  get f() { return this.enterpriseForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.enterpriseForm.invalid) {
      return;
    }

    let nomComercial = this.enterpriseForm.get('nomComercial').value;
    let nif = this.enterpriseForm.get('nif').value;
    let cnae = this.enterpriseForm.get('cnae').value;
    let activity = this.enterpriseForm.get('activity').value;
    let location = this.enterpriseForm.get('location').value;
    let enterprisePhone = this.enterpriseForm.get('enterprisePhone').value;
    let enterpriseEmail = this.enterpriseForm.get('enterpriseEmail').value;


    this.enterprise = new Enterprise(nomComercial,nif,cnae,activity,location,enterprisePhone,enterpriseEmail);

    this.username = sessionStorage.getItem('AuthUsername');

    console.log(this.username);

    this.enterpriseService.getIdUsername(this.username).subscribe(
      data => {
        this.userId = data.id

        if (data.isEnterprise == 2 || data.isEnterprise == 1){
          this.Message = "Ya has registrado una empresa o está en proceso de admisión";
          return
        }
        
        this.enterpriseService.putUserDetailsToEnterprise(this.enterprise,this.userId).subscribe(
          data => {
            this.enterprise = data;
            console.log(this.enterprise);
            this.isRegisterFail = true;
            this.Message = "Solicitud de registro realizada";
          }, err => {
            this.isRegisterFail = false;
            this.Message = err.error.mensaje;
          }
        );
      }
    );

    setTimeout( () => { 
      this.submitted = false;
      this.Message = "";
     },3000);
  
    
  }

  isEnterpriseCheck(){
    this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
      data => {

        if (data.isEnterprise == 2){
          this.enterpriseStatus = "Pendiente";
          this.inactiveEnterprise = true;
         
        } else if(data.isEnterprise == 1) {
          this.enterpriseStatus = "Registrado";
          this.isEnterpriseChk = true;
        } else {
          this.enterpriseStatus = "No solicitado";
        }

      }
    );

  }

  deleteEnterprise(){
    console.log("hola")
      this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
        data => {
          this.adminService.putEnterpriseStatus(data.id,2).subscribe();
          window.location.reload();
        }
      );
    
  }


  deleteEnterpriseFromSystem() {
    this.enterpriseService.getIdUsername(sessionStorage.getItem('AuthUsername')).subscribe(
      data => {
        this.adminService.putEnterpriseStatus(data.id,0).subscribe();
        window.location.reload();
      }
    );
  }
}

