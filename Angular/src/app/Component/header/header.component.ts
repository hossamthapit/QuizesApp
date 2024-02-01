import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NewAuthService } from '../../Auths/new-auth.service';
import { StorageService } from '../../Auths/storage.service';
import { Roles, User } from '../../../Models/User';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink,HttpClientModule,CommonModule,RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})

export class HeaderComponent {

  constructor(public authService: NewAuthService) {}

  user!: User;

  ngOnInit(){
    this.user = this.authService.getUser();
  }

  logout(){
    this.authService.logout();
  }

  isLoggedIn(){
    return this.authService.isLoggedIn();
  }
}
