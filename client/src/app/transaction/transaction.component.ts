import { Component, OnInit } from '@angular/core';
import {TransactionService } from '../shared/transaction/transaction.service';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../globals';
import { Router } from '@angular/router';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.scss']
})
export class TransactionComponent implements OnInit {

  constructor(private transactionService: TransactionService, 
    private http: HttpClient, private router: Router, private globals: Globals) {
    if (this.globals.authenticated == false) {
      this.router.navigateByUrl('/login');
      return;
    } 

    this.loadTransactions();
  }

  transactions = null;

  loadTransactions() {
    var result;
    result = this.transactionService.getTransactionList().subscribe(data => {
      this.transactions = data;
      console.log(this.transactions);
    });

  }


  ngOnInit() {
  }

}
