import { Component, OnInit } from '@angular/core';
import { Globals } from '../globals';
import { Router } from '@angular/router';
import { TransactionService } from '../shared/transaction/transaction.service';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.scss']
})
export class TransferComponent implements OnInit {

  transferData = {title: '', toAccount: 0, amount: 5};
  paragraphText = '';

  constructor(private router: Router, private globals: Globals, private transactionService : TransactionService) { 
    if (this.globals.authenticated == false) {
      this.router.navigateByUrl('/login');
    }
  }


  ngOnInit() {
  }

  transfer() {
    var result;
    result = this.transactionService.transfer(this.transferData).subscribe(data => {
      console.log(data);
      this.paragraphText = data['status'];

      if (data['status'] == "OK") {
        this.router.navigateByUrl('/transactions');
      }
    });
  }

}
