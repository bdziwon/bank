import { Injectable } from '@angular/core';
import { Globals } from '../../globals';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http: HttpClient, 
    private globals: Globals) { }  

  getTransactionList() {
    const headers = this.globals.headers;
    return this.http.get('//localhost:8080/transactions', {headers});
  }

  transfer(transferData) {
    const headers = this.globals.headers;
    return this.http.post('//localhost:8080/transfer', JSON.stringify(transferData), {headers});
  }
}
