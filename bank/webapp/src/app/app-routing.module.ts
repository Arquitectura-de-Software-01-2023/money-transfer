import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountsComponent } from './pages/accounts/accounts.component';
import { TransfersComponent } from './pages/transfers/transfers.component';
import { AuthGuard } from './guards/auth.guard';
import { TransferByAccountComponent } from './pages/transfer-by-account/transfer-by-account.component';

const routes: Routes = [
  { path: 'cuentas', component: AccountsComponent, canActivate: [AuthGuard] },
  { path: 'transferencias', component: TransfersComponent, canActivate: [AuthGuard] },
  { path: 'cuentas/:id', component: TransferByAccountComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
