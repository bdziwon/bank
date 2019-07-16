import { Injectable } from '@angular/core';

@Injectable()
export class Globals {
    credentials = {username: '', password: ''};
    authenticated = false;
    user = null;
    headers = null;
}