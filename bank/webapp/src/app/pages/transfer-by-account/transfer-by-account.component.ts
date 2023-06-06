import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Transfer } from 'src/app/interfaces/transfer';
import { TransfersService } from 'src/app/services/transfers.service';

@Component({
  selector: 'app-transfer-by-account',
  templateUrl: './transfer-by-account.component.html'
})
export class TransferByAccountComponent implements OnInit { 
  accountId: string = '';
  title: string = '';
  transfers: Transfer[] = [];
  
  constructor(
    private route: ActivatedRoute,
    private transferService: TransfersService
  ) { 

  }

  ngOnInit(): void { 
    this.route.params.subscribe(params => { 
      this.accountId = params['id'];
      this.title = `Transferencias de la cuenta ${this.accountId}`;
      this.loadTransfers();
    });
  }

  loadTransfers() {
    this.transferService.getTransfersByAccount(this.accountId).subscribe({ next: (transfers) => this.transfers = transfers});
  }
}
