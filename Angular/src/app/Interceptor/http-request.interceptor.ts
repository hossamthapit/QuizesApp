import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { StorageService } from '../Auths/storage.service';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { NewAuthService } from '../Auths/new-auth.service';


@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
  private isRefreshing = false;

  constructor(
    private storageService: StorageService,
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log("intercepted",req);
    req = req.clone({
      withCredentials: true,
      headers: this.addTokenHeader(req.headers),
    });

    return next.handle(req).pipe(
      catchError((error) => { 
        if ( 
          error instanceof HttpErrorResponse &&
          !req.url.includes('auth/login') &&
          error.status === 401
        ) {
          return this.handle401Error(req, next);
        }

        return throwError(() => error);
      })
    );
  }

  private addTokenHeader(headers: HttpHeaders): HttpHeaders {
    const token = this.storageService.getItem<string>('token');
    if (token) 
      headers = headers.set('Authorization', `Bearer ${token}`);
    return headers;
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    console.log("error happened in intercetor");
    if (!this.isRefreshing) {
      this.isRefreshing = true;
    }
    return next.handle(request);
  }
}
