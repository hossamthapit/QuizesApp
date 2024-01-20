import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NewAuthService } from '../../Auths/new-auth.service';
import { StorageService } from '../../Auths/storage.service';
import { Roles } from '../../../Models/User';
import { ModalComponent } from '../modal/modal.component';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink,HttpClientModule,CommonModule,RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {

  firstName: string | null = null;
  lastName: string | null = null;
  role: Roles | null = null;
  imageUrl : string = "assets/student.jpg";
  objectType : string = "Student";


  constructor(public authService: NewAuthService, public storageService: StorageService,private route: ActivatedRoute) {
    
  }

  ngOnInit(){
  }

  ngOnChange(){
    this.role = this.storageService.getUser().role;
    if(this.role == Roles.teacher)this.objectType = "Teacher";
    else if(this.role == Roles.admin)this.objectType = "Admin";
  }

  logout(){
    this.authService.logout();
    this.storageService.setAuthState(false);

  }

  isLoggedIn(){
    return this.authService.isLoggedIn()
  }
}
