import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISharedAgent, SharedAgent } from 'app/shared/model/shared-agent.model';
import { SharedAgentService } from './shared-agent.service';
import { SharedAgentComponent } from './shared-agent.component';
import { SharedAgentDetailComponent } from './shared-agent-detail.component';
import { SharedAgentUpdateComponent } from './shared-agent-update.component';

@Injectable({ providedIn: 'root' })
export class SharedAgentResolve implements Resolve<ISharedAgent> {
  constructor(private service: SharedAgentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISharedAgent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sharedAgent: HttpResponse<SharedAgent>) => {
          if (sharedAgent.body) {
            return of(sharedAgent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SharedAgent());
  }
}

export const sharedAgentRoute: Routes = [
  {
    path: '',
    component: SharedAgentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jeevesdemoApp.sharedAgent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SharedAgentDetailComponent,
    resolve: {
      sharedAgent: SharedAgentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jeevesdemoApp.sharedAgent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SharedAgentUpdateComponent,
    resolve: {
      sharedAgent: SharedAgentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jeevesdemoApp.sharedAgent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SharedAgentUpdateComponent,
    resolve: {
      sharedAgent: SharedAgentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jeevesdemoApp.sharedAgent.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
