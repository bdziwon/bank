import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { Globals } from '../globals';
import { RegisterService } from '../shared/register/register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {

  registerData = {login: '', password: '', repeatPassword: '', name: '',
      surname: '', accountType: '', percentage: 0.0};

  paragraphText = '';
  
  selectOptions = [{value: "Normal", viewValue: "Zwykłe"},
    {value: "Investement", viewValue: "Lokata"},
    {value: "Saving", viewValue: "Oszczędnościowe"}
  ]

  constructor(private router: Router, private globals: Globals, private registerService : RegisterService) { 
     if (this.globals.authenticated == true) {
       this.router.navigateByUrl('');
     }
  }

  register() {
    var result;
    result = this.registerService.register(this.registerData).subscribe(data => {
      console.log(data);
      this.paragraphText = data['status'];

      if (data['status'] == "OK") {
        this.router.navigateByUrl('/login');
      }

    });

  }

  ngOnInit() {
  }
}
