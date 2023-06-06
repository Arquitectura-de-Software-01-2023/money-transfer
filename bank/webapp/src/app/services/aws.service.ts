import { Injectable } from '@angular/core';
import { HttpClient, HttpStatusCode } from '@angular/common/http';
import { Currency } from '../interfaces/currency';
import { Bank } from '../interfaces/bank';
import { response } from '../interfaces/response';
import { Observable } from 'rxjs';

const banks: Bank[] = [
  { code: 'BEC', name: 'Banco de Achumani' }
]

@Injectable({
  providedIn: 'root'
})

export class AwsService {

  constructor(private client: HttpClient) { 
  }

  getCurrencies() : Observable<Currency[]> {
    return this.client.get<Currency[]>('http://localhost/accounts/currencies')
  }

  getBanks(): response<Bank[]> {
    return {
      data: banks,
      status: HttpStatusCode.Ok
    };
  }
}
