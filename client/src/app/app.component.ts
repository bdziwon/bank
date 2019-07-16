import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginService } from './shared/login/login.service';
import 'rxjs/add/operator/finally';
import { Globals } from './globals';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent {
  title = 'client';

  public authenticated = this.globals.authenticated;
  constructor(private app: LoginService, private http: HttpClient, private router: Router, private globals: Globals ) {
    if (this.globals.authenticated == false) {
      this.router.navigateByUrl('/login');
      return;
    }
  }

  logout() {
    this.globals.authenticated = false;
    this.router.navigateByUrl('/login');
  }
}


