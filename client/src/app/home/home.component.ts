import { Component, OnInit } from '@angular/core';
import { HomeService } from '../shared/home/home.service';
import { HttpClient } from '@angular/common/http';
import { Globals } from '../globals';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']

})
export class HomeComponent implements OnInit {

  transferHidden = false;
  title = 'Home';
  userInfo = {login : '', creationDate : "", number : -1, name : '', surname : '', balance : '', role : '', percentage : 0, accountType : ''};

  constructor(private homeService: HomeService, private http: HttpClient, 
    private router: Router, private globals: Globals) {
    if (this.globals.authenticated == false) {
      this.router.navigateByUrl('/login');
      return;
    } 

    this.loadHome();

  }

  loadHome() {
    var result;
    result = this.homeService.getLoggedUser().subscribe(data => {
      console.log(data);  
      this.userInfo.login = data['login'];
      this.userInfo.name = data['name'];
      this.userInfo.surname = data['surname'];
      this.userInfo.balance = data['balance'];
      this.userInfo.percentage = data['percentage'];
      this.userInfo.accountType = data['accountType'];
      this.userInfo.role = data['roles'][0];
      this.userInfo.number = data['number'];
      this.userInfo.creationDate = data['creationDate'];

      if (this.userInfo.accountType == 'Investement') {
        this.transferHidden = true;
      }
    });
  }


  ngOnInit() {
  }

}
