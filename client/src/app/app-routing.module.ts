import { NgModule } from '@angular/core';
import { Routes, RouterModule, Router } from '@angular/router';
import { Globals } from './globals';

const routes: Routes = [];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
  constructor(private router: Router, private globals: Globals ) {
    if (this.globals.authenticated == false) {
      this.router.navigateByUrl('/login');
    }
  }
}
