import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'shared-agent',
        loadChildren: () => import('./shared-agent/shared-agent.module').then(m => m.JeevesdemoSharedAgentModule)
      },
      {
        path: 'ssh-credentials',
        loadChildren: () => import('./ssh-credentials/ssh-credentials.module').then(m => m.JeevesdemoSshCredentialsModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JeevesdemoEntityModule {}
