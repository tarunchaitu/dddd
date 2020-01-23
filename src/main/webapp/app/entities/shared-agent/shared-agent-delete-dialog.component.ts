import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISharedAgent } from 'app/shared/model/shared-agent.model';
import { SharedAgentService } from './shared-agent.service';

@Component({
  templateUrl: './shared-agent-delete-dialog.component.html'
})
export class SharedAgentDeleteDialogComponent {
  sharedAgent?: ISharedAgent;

  constructor(
    protected sharedAgentService: SharedAgentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sharedAgentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sharedAgentListModification');
      this.activeModal.close();
    });
  }
}
