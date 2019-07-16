import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';
import { Globals } from '../../globals';
import 'rxjs/add/operator/map'

@Injectable({
  providedIn: 'root'
})

export class RegisterService {

  constructor(private http: HttpClient, private router: Router, 
    private globals: Globals) {
  }

  register(registerData) {
    console.log("registerData: " + JSON.stringify(registerData));
    return this.http.post('//localhost:8080/register', JSON.stringify(registerData));
  }
}
