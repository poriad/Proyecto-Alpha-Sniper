import { animate, style, transition, trigger } from '@angular/animations';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ContactCVForm, ContactForm } from 'src/app/interfaces/contact-interface';
import { EmailService } from 'src/app/service/email.service';
import { requiredFileType } from 'src/app/utils/validador';
import { faDownload } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
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
export class ContactComponent implements OnInit {
  faDownload = faDownload;
  contactForm: FormGroup;
  jobForm: FormGroup;
  submitted = false;
  submittedJob = false;

  SERVER_URL = "http://localhost:8082/files/uploadFile";

  //SERVER_URL = "http://192.168.1.134:8082/files/uploadFile";

  //SERVER_URL = "http://localhost:8082/snowyduneservice/files/uploadFile";

  //SERVER_URL = "http://iesalixar.ddns.net:9095/snowyduneservice/files/uploadFile";


  constructor(private formBuilder: FormBuilder, private toastr: ToastrService, private emailService: EmailService, private httpClient: HttpClient) { }

  ngOnInit(): void {

    this.jobForm = this.formBuilder.group({
      name: ['', Validators.required],
      surName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      cv: ['', [Validators.required, requiredFileType('pdf')]]
    });

    this.contactForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      description: ['', [Validators.required, Validators.minLength(30)]]
    });

  }

  get f() { return this.contactForm.controls; }
  get fj() { return this.jobForm.controls; }

  onSubmit() {

    this.submitted = true;

    if (this.contactForm.invalid) {
      return;
    }
    let nameForm = this.contactForm.get('name').value;
    let emailForm = this.contactForm.get('email').value;
    let commentForm = this.contactForm.get('description').value;

    let contact = <ContactForm>{
      "name": nameForm,
      "email": emailForm,
      "comment": commentForm
    }

    this.emailService.postEmailContact(contact).subscribe(
      data => {
        data = contact;
        this.contactForm.reset();
        this.toastr.success('Se ha enviado su comentario a nuestros administradores', 'Contacto', {
          timeOut: 3000,
        });
      }
    )

    this.submitted = false;
  }

  onSubmitCV() {

    this.submittedJob = true;

    if (this.jobForm.invalid) {
      return;
    }

    let name = this.jobForm.get('name').value;
    let email = this.jobForm.get('email').value;
    let filename = this.jobForm.get('cv').value;



    const formData = new FormData();
    formData.append('file', this.jobForm.get('cv').value);
    formData.append('name', this.jobForm.get('name').value);
    formData.append('email', this.jobForm.get('email').value);

    this.httpClient.post<any>(this.SERVER_URL, formData).subscribe(
      (res) => {
        console.log(filename);
        this.jobForm.reset();
        this.toastr.success('Su curriculum se ha enviado correctamente', 'Curriculum', {
          timeOut: 3000,
        });

      },
      (err) => console.log(err),

    );

    this.submittedJob = false;
  }

  onFileSelect(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.jobForm.get('cv').setValue(file);
    }
  }
}
