import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { PostTransactionRequest } from '../interfaces/post-transaction-request';
import { Transfer } from '../interfaces/transfer';

@Injectable({
  providedIn: 'root'
})
export class TransfersService {

  constructor(private client: HttpClient) {
  }

  createTransfer(body: PostTransactionRequest) : Observable<Transfer>{
    return this.client.post<Transfer>('http://localhost/transfers', body)
  }

  getTransfersByAccount(account: string) : Observable<Transfer[]>{
    return this.client.get<Transfer[]>(`http://localhost/transfers/${account}`)
  }
}
