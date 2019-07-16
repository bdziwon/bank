import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AccountService } from './shared/account/account.service';
import {AccountListComponent} from './account-list/account-list.component';

import { HttpClientModule } from '@angular/common/http';
import { fromEventPattern } from 'rxjs';
import { MatButtonModule, MatCardModule, MatSelectModule, MatInputModule, MatOptionModule, MatListModule, MatToolbarModule, MatTableModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule } from '@angular/forms';
import { Globals } from './globals';
import { TransactionComponent } from './transaction/transaction.component';
import { TransferComponent } from './transfer/transfer.component';

const appRoutes: Routes = [
  {
    path: 'accounts',
    component: AccountListComponent
  },
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'transactions',
    component: TransactionComponent
  },
  {
    path: 'transfer',
    component: TransferComponent
  }
  // {
  //   path: 'car-edit/:id',
  //   component: CarEditComponent
  // }
];

@NgModule({
  declarations: [
    AppComponent,
    AccountListComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    TransactionComponent,
    TransferComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatListModule,
    MatToolbarModule,
    MatInputModule,
    RouterModule.forRoot(appRoutes),
    FormsModule,
    MatTableModule,
    MatOptionModule,
    MatSelectModule
  ],
    providers: [AccountService, Globals],
  bootstrap: [AppComponent]
})
export class AppModule { }


