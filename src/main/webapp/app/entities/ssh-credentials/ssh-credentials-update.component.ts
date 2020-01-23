import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISshCredentials, SshCredentials } from 'app/shared/model/ssh-credentials.model';
import { SshCredentialsService } from './ssh-credentials.service';

@Component({
  selector: 'jhi-ssh-credentials-update',
  templateUrl: './ssh-credentials-update.component.html'
})
export class SshCredentialsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    username: [null, [Validators.required]],
    password: [],
    sshKey: []
  });

  constructor(protected sshCredentialsService: SshCredentialsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sshCredentials }) => {
      this.updateForm(sshCredentials);
    });
  }

  updateForm(sshCredentials: ISshCredentials): void {
    this.editForm.patchValue({
      id: sshCredentials.id,
      name: sshCredentials.name,
      username: sshCredentials.username,
      password: sshCredentials.password,
      sshKey: sshCredentials.sshKey
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sshCredentials = this.createFromForm();
    if (sshCredentials.id !== undefined) {
      this.subscribeToSaveResponse(this.sshCredentialsService.update(sshCredentials));
    } else {
      this.subscribeToSaveResponse(this.sshCredentialsService.create(sshCredentials));
    }
  }

  private createFromForm(): ISshCredentials {
    return {
      ...new SshCredentials(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
      sshKey: this.editForm.get(['sshKey'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISshCredentials>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
