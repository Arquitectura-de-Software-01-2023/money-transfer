import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ClrLoadingState } from '@clr/angular';
import { Currency } from 'src/app/interfaces/currency';
import { AwsService } from 'src/app/services/aws.service';
import { AccountsService } from 'src/app/services/accounts.service';
import { Clipboard } from '@angular/cdk/clipboard';
import { Account } from 'src/app/interfaces/account';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html'
})
export class AccountsComponent {
  isCreatingAccount = false;
  submitBtnState: ClrLoadingState = ClrLoadingState.DEFAULT;
  createForm : FormGroup;
  currencies: Currency[] = [];
  accounts: Account[] = [];

  constructor(
    private awsService: AwsService,
    private accountService: AccountsService,
    private clipboard: Clipboard
    
  ) { 
    this.awsService.getCurrencies().subscribe({ next: (currencies) => 
      {
        this.currencies = currencies;
        console.log(currencies);
      }
    });
    this.createForm = new FormBuilder().group({
      currency: ['BOB', Validators.required],
    });

    this.loadAccounts();
  }

  async createAccount() {
    this.submitBtnState = ClrLoadingState.LOADING;
    this.accountService.createAccount(this.createForm.value).subscribe({
      next: () => { 
        this.isCreatingAccount = false;
        this.submitBtnState = ClrLoadingState.SUCCESS;
        this.loadAccounts();
      },
      error: () => { this.submitBtnState = ClrLoadingState.ERROR; alert('Error creating account!'); }
    });
  }

  async loadAccounts() {
    this.accountService.getAccounts().subscribe({ next: (accounts) => this.accounts = accounts});
  }

  copyText(text: string) {
    this.clipboard.copy(text);
  }
}
