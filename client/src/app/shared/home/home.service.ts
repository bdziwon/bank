import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';
import { Globals } from '../../globals';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private http: HttpClient, private router: Router, 
    private globals: Globals) { }

    getLoggedUser() {
      const headers = this.globals.headers;
      
      return this.http.get('//localhost:8080', {headers});
    }

}
