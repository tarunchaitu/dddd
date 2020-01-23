import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISshCredentials } from 'app/shared/model/ssh-credentials.model';
import { SshCredentialsService } from './ssh-credentials.service';

@Component({
  templateUrl: './ssh-credentials-delete-dialog.component.html'
})
export class SshCredentialsDeleteDialogComponent {
  sshCredentials?: ISshCredentials;

  constructor(
    protected sshCredentialsService: SshCredentialsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sshCredentialsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sshCredentialsListModification');
      this.activeModal.close();
    });
  }
}
