import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISshCredentials, SshCredentials } from 'app/shared/model/ssh-credentials.model';
import { SshCredentialsService } from './ssh-credentials.service';
import { SshCredentialsComponent } from './ssh-credentials.component';
import { SshCredentialsDetailComponent } from './ssh-credentials-detail.component';
import { SshCredentialsUpdateComponent } from './ssh-credentials-update.component';

@Injectable({ providedIn: 'root' })
export class SshCredentialsResolve implements Resolve<ISshCredentials> {
  constructor(private service: SshCredentialsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISshCredentials> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sshCredentials: HttpResponse<SshCredentials>) => {
          if (sshCredentials.body) {
            return of(sshCredentials.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SshCredentials());
  }
}

export const sshCredentialsRoute: Routes = [
  {
    path: '',
    component: SshCredentialsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jeevesdemoApp.sshCredentials.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SshCredentialsDetailComponent,
    resolve: {
      sshCredentials: SshCredentialsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jeevesdemoApp.sshCredentials.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SshCredentialsUpdateComponent,
    resolve: {
      sshCredentials: SshCredentialsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jeevesdemoApp.sshCredentials.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SshCredentialsUpdateComponent,
    resolve: {
      sshCredentials: SshCredentialsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jeevesdemoApp.sshCredentials.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
