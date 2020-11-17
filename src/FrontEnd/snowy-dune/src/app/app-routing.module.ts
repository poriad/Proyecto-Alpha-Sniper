import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CheckoutComponent } from './layouts/checkout/checkout.component';
import { HomeUserComponent } from './layouts/home-user/home-user.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './layouts/register/register.component';
import { StationComponent } from './layouts/station/station.component';
import { TripComponent } from './layouts/trip/trip.component';
import { AdminServicesCommentsComponent } from './layouts/admin-services-comments/admin-services-comments.component';
import { AdminUsersEnterprisesComponent } from './layouts/admin-users-enterprises/admin-users-enterprises.component';
import { CartComponent } from './layouts/cart/cart.component';
import { ContactComponent } from './layouts/contact/contact.component';
import { HomeAdminComponent } from './layouts/home-admin/home-admin.component';
import { HomeEnterpriseComponent } from './layouts/home-enterprise/home-enterprise.component';
import { UserManagementComponent } from './layouts/user-management/user-management.component';
import { ProdGuardService as guard } from './guards/prod-guard.service'
import { StationCardComponent } from './components/station-card/station-card.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'Register', component: RegisterComponent },
  { path: 'Contact', component: ContactComponent },
  { path: 'HomeUser', component: HomeUserComponent, canActivate: [guard], data: { expectedRol: ['admin','enterprise','user']}},
  { path: 'Station', component: StationComponent, canActivate: [guard], data: { expectedRol: ['admin','enterprise','user']}},
  { path: 'Station/search/:keyword', component: StationComponent, canActivate: [guard], data: { expectedRol: ['admin','enterprise','user']}},
  { path: 'Checkout', component: CheckoutComponent,canActivate: [guard], data: { expectedRol: ['admin','enterprise','user']}},
  { path: 'Trip', component: TripComponent, canActivate: [guard], data: { expectedRol: ['admin','enterprise','user']}},
  { path: 'ManagementUser', component: UserManagementComponent, canActivate: [guard], data: { expectedRol: ['admin','enterprise','user']}},
  { path: 'Cart', component: CartComponent, canActivate: [guard], data: { expectedRol: ['admin','enterprise','user']}},
  { path: 'HomeEnterprise', component: HomeEnterpriseComponent, canActivate: [guard], data: { expectedRol: ['admin','enterprise']}},
  { path: 'HomeAdmin', component: HomeAdminComponent,canActivate: [guard], data: { expectedRol: ['admin','enterprise']}},
  { path: 'UsersEnterprisesAdmin', component: AdminUsersEnterprisesComponent,canActivate: [guard], data: { expectedRol: 'admin'}},
  { path: 'ServicesCommentsAdmin', component: AdminServicesCommentsComponent,canActivate: [guard], data: { expectedRol: 'admin'}},
  { path: '**', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
