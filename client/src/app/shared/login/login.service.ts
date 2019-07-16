import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';
import { Globals } from '../../globals';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private router: Router, private globals: Globals) {
  }

  authenticate(credentials) {

        var result = 1;

        const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password) });
        this.http.get('//localhost:8080', {headers}).subscribe(response => {

            this.globals.credentials = credentials;
            this.globals.authenticated = true;
            this.globals.user = response;
            this.globals.headers = headers;
            this.router.navigateByUrl('');

            result = 0;


        }, error => { 
            console.log(error); 
            this.globals.authenticated = false;
            result = 1;
        })

        return result;

    }

}
