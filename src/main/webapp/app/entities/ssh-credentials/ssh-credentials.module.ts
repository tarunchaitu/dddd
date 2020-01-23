import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JeevesdemoSharedModule } from 'app/shared/shared.module';
import { SshCredentialsComponent } from './ssh-credentials.component';
import { SshCredentialsDetailComponent } from './ssh-credentials-detail.component';
import { SshCredentialsUpdateComponent } from './ssh-credentials-update.component';
import { SshCredentialsDeleteDialogComponent } from './ssh-credentials-delete-dialog.component';
import { sshCredentialsRoute } from './ssh-credentials.route';

@NgModule({
  imports: [JeevesdemoSharedModule, RouterModule.forChild(sshCredentialsRoute)],
  declarations: [
    SshCredentialsComponent,
    SshCredentialsDetailComponent,
    SshCredentialsUpdateComponent,
    SshCredentialsDeleteDialogComponent
  ],
  entryComponents: [SshCredentialsDeleteDialogComponent]
})
export class JeevesdemoSshCredentialsModule {}
