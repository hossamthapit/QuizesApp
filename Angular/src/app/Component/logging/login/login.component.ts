import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NewAuthService } from '../../../Auths/new-auth.service';
import { HttpClientModule } from '@angular/common/http';
import { StorageService } from '../../../Auths/storage.service';
import { Router } from '@angular/router';
import { HeaderComponent } from '../../header/header.component';
import { InputRequiredDirective } from '../../../Directives/input-required.directive';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    HeaderComponent,
    InputRequiredDirective
  ]
})
export class LoginComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private authService: NewAuthService,
    private storageService: StorageService,
    private router: Router
  ) {}

  loginForm!: FormGroup;
  private apiUrl = 'http://localhost:8080/api/groups'; // URL for the groups endpoint

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      id: 0,
      email: ['hossamthapit0@gmail.com', [Validators.required,Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
      password: ['12345678', [Validators.required, Validators.minLength(8)]],
    });
  }

  onSubmit(): void {
    this.authService.login(this.loginForm.value).subscribe(
      (data) => {
        this.storageService.setItem('token', data.token);
        this.storageService.setItem('email', data.email);
        this.storageService.setItem('firstName', data.user.firstName);
        this.storageService.setItem('lastName', data.user.lastName);
        this.storageService.setItem('roles', data.user.roles);
        this.storageService.setItem('id', data.user.id);
        this.storageService.setItem('pictureUrl', data.user.pictureUrl);
        this.storageService.setItem('user', data.user);
        this.storageService.setAuthState(true);
        this.router.navigateByUrl('/groups');
      }
    )
  }
}
