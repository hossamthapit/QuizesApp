import { Injectable} from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { NewAuthService } from '../Auths/new-auth.service';
import { Observable } from 'rxjs';
import { StorageService } from '../Auths/storage.service';

@Injectable({
  providedIn: 'root'
})

export class CanActivateRouteGuardService implements CanActivate {

  routeURL: string = '/';
  constructor(private storageService: StorageService, private router: Router) {
    this.routeURL = this.router.url;
  }
  
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    console.log('routdasdURL: ', state.url);
    if (!this.storageService.isLoggedIn() && (state.url !== '' && state.url !== '/')) {
      this.router.navigate(['']);
      return false;
    } 
    else if (this.storageService.isLoggedIn() && (state.url === '' || state.url === '/')) {
      return false;
    } 
    else {
      console.log('routeURL: ', this.storageService.isLoggedIn());
      //this.router.navigate([`${state.url}`]);
      return true;
    }
    }
}

