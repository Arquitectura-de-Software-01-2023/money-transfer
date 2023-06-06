import { HttpClient, HttpStatusCode } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PostAccountRequest } from '../interfaces/post-account-request';
import { Account } from '../interfaces/account';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class AccountsService {

  constructor(private client: HttpClient) { }

  createAccount(body: PostAccountRequest) : Observable<Account>{
    return this.client.post<Account>('http://localhost/accounts', body)
  }

  getAccounts() : Observable<Account[]> {
    return this.client.get<Account[]>('http://localhost/accounts');
  }
}
