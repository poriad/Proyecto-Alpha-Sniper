import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class FooterComponent implements OnInit {

  isChecked = false;

  constructor(public translate: TranslateService) {
    translate.addLangs(['es', 'en']);
    translate.setDefaultLang('en');

    translate.use('es');
    //const browserLang = translate.getBrowserLang();
    //translate.use(browserLang.match(/es|en/) ? browserLang : 'es');
  }

  ngOnInit(): void {
    
  }


  changeLang(){
    if(this.isChecked) {
      this.translate.use('en');
    } else {
      this.translate.use('es');
    }

  }

  
}
