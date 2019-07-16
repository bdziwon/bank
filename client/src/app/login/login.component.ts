import { Component, OnInit } from '@angular/core';
import { LoginService } from '../shared/login/login.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Globals } from '../globals';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  credentials = {username: '', password: ''};
  paragraphText = '';

  constructor(private app: LoginService, private globals : Globals, private http: HttpClient, private router: Router) {
    if (this.globals.authenticated == true) {
      this.router.navigateByUrl('');
    }
  }

  login() {
    var result;
    result = this.app.authenticate(this.credentials);

    console.log(result);
    if (result == 1) {
        this.paragraphText = "Nieprawidłowa nazwa użytkownika lub hasło.";
    } else {
      this.paragraphText = "Zalogowano.";
    }

  }
  ngOnInit() {
  }

}
