import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})

export class AccountService {

  constructor(private http: HttpClient) { 
  }

  getAll(): Observable<any> {
    return this.http.get('//localhost:8080/accounts');
  }

}