import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JeevesdemoSharedModule } from 'app/shared/shared.module';
import { SharedAgentComponent } from './shared-agent.component';
import { SharedAgentDetailComponent } from './shared-agent-detail.component';
import { SharedAgentUpdateComponent } from './shared-agent-update.component';
import { SharedAgentDeleteDialogComponent } from './shared-agent-delete-dialog.component';
import { sharedAgentRoute } from './shared-agent.route';

@NgModule({
  imports: [JeevesdemoSharedModule, RouterModule.forChild(sharedAgentRoute)],
  declarations: [SharedAgentComponent, SharedAgentDetailComponent, SharedAgentUpdateComponent, SharedAgentDeleteDialogComponent],
  entryComponents: [SharedAgentDeleteDialogComponent]
})
export class JeevesdemoSharedAgentModule {}
