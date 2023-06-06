import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Account } from 'src/app/interfaces/account';
import { Bank } from 'src/app/interfaces/bank';
import { AccountsService } from 'src/app/services/accounts.service';
import { AwsService } from 'src/app/services/aws.service';
import { TransfersService } from 'src/app/services/transfers.service';

@Component({
  selector: 'app-transfers',
  templateUrl: './transfers.component.html'
})
export class TransfersComponent {
  accounts: Account[] = [];
  banks: Bank[] = [];
  transferForm: FormGroup;

  constructor(
    private accountService: AccountsService,
    private awsService: AwsService,
    private transferService: TransfersService
  ) { 
    this.loadAccounts();
    this.banks = awsService.getBanks().data ?? [];
    this.transferForm = new FormBuilder().group({
      sourceAccount: ['', Validators.required],
      targetBank: ['', Validators.required],
      targetAccount: ['', Validators.required],
      amount: ['', Validators.required]
     });
  }

  async loadAccounts() {
    this.accountService.getAccounts().subscribe({ next: (accounts) => this.accounts = accounts});
  }

  createTransfer() {
    this.transferService.createTransfer(this.transferForm.value).subscribe({ 
      next: () => {
        this.transferForm.reset();
      }, 
      error: () => alert('Error creating transfer!')}
    );
  }
}
