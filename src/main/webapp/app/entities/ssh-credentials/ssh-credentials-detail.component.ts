import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISshCredentials } from 'app/shared/model/ssh-credentials.model';

@Component({
  selector: 'jhi-ssh-credentials-detail',
  templateUrl: './ssh-credentials-detail.component.html'
})
export class SshCredentialsDetailComponent implements OnInit {
  sshCredentials: ISshCredentials | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sshCredentials }) => {
      this.sshCredentials = sshCredentials;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
