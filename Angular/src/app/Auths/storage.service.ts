import { Injectable } from '@angular/core';
import { NewAuthService } from './new-auth.service';
import { Roles, User } from '../../Models/User';

@Injectable({
  providedIn: 'root',
})
export class StorageService {

  loggedUser: User | null = null;

  private readonly AUTH_KEY = 'auth';

  private readonly USER_KEY = 'user';

  constructor() {}

  setUser(user: any): void {
    localStorage.setItem(this.USER_KEY, JSON.stringify(user));
  }

  getUser(): User {
    const userString = localStorage.getItem(this.USER_KEY);
    return userString ? JSON.parse(userString) : null;
  }

  clearUser(): void {
    localStorage.removeItem(this.USER_KEY);
  }
  setAuthState(isLoggedIn: boolean): void {
    localStorage.setItem(this.AUTH_KEY, isLoggedIn ? 'true' : 'false');
  }

  isLoggedIn(): boolean {
    const authState = localStorage.getItem(this.AUTH_KEY);
    return authState === 'true';
  }

  clearAuthState(): void {
    localStorage.removeItem(this.AUTH_KEY);
  }

  setItem(key: string, value: any): void {
    localStorage.setItem(key, JSON.stringify(value));
  }

  getItem<T>(key: string): T | null {
    const storedItem = localStorage.getItem(key);
    return storedItem ? (JSON.parse(storedItem) as T) : null;
  }

  removeItem(key: string): void {
    localStorage.removeItem(key);
  }

  clear(): void {
    localStorage.clear();
  }

  getEmail(): string | null {
    const email = this.getItem<string>('email');
    return email;
  }
  getToken(): string | null {
    const token = this.getItem<string>('token');
    return token;
  }
  getFirstName(): string | null {
    const firstName = this.getItem<string>('firstName');
    return firstName;
  }
  getlastName(): string | null {
    const lastName = this.getItem<string>('lastName');
    return lastName;
  }
  getRoles(): Roles | null {
    const roles = this.getItem<Roles>('roles');
    return roles;
  }
  getProfilePicture(): string | null {
    const pic = this.getItem<string>('pictureUrl');
    return pic;
  }

  
}
