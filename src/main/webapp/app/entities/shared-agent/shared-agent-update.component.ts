import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISharedAgent, SharedAgent } from 'app/shared/model/shared-agent.model';
import { SharedAgentService } from './shared-agent.service';
import { ISshCredentials } from 'app/shared/model/ssh-credentials.model';
import { SshCredentialsService } from 'app/entities/ssh-credentials/ssh-credentials.service';

@Component({
  selector: 'jhi-shared-agent-update',
  templateUrl: './shared-agent-update.component.html'
})
export class SharedAgentUpdateComponent implements OnInit {
  isSaving = false;

  sshcredentials: ISshCredentials[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    noOfExecutors: [null, [Validators.required]],
    label: [null, [Validators.required]],
    workspace: [null, [Validators.required]],
    dnsName: [null, [Validators.required]],
    agentState: [null, [Validators.required]],
    bootstrapMetod: [null, [Validators.required]],
    os: [null, [Validators.required]],
    sshPort: [],
    jvmOptions: [],
    sshCredentials: []
  });

  constructor(
    protected sharedAgentService: SharedAgentService,
    protected sshCredentialsService: SshCredentialsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sharedAgent }) => {
      this.updateForm(sharedAgent);

      this.sshCredentialsService
        .query()
        .pipe(
          map((res: HttpResponse<ISshCredentials[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ISshCredentials[]) => (this.sshcredentials = resBody));
    });
  }

  updateForm(sharedAgent: ISharedAgent): void {
    this.editForm.patchValue({
      id: sharedAgent.id,
      name: sharedAgent.name,
      description: sharedAgent.description,
      noOfExecutors: sharedAgent.noOfExecutors,
      label: sharedAgent.label,
      workspace: sharedAgent.workspace,
      dnsName: sharedAgent.dnsName,
      agentState: sharedAgent.agentState,
      bootstrapMetod: sharedAgent.bootstrapMetod,
      os: sharedAgent.os,
      sshPort: sharedAgent.sshPort,
      jvmOptions: sharedAgent.jvmOptions,
      sshCredentials: sharedAgent.sshCredentials
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sharedAgent = this.createFromForm();
    if (sharedAgent.id !== undefined) {
      this.subscribeToSaveResponse(this.sharedAgentService.update(sharedAgent));
    } else {
      this.subscribeToSaveResponse(this.sharedAgentService.create(sharedAgent));
    }
  }

  private createFromForm(): ISharedAgent {
    return {
      ...new SharedAgent(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      noOfExecutors: this.editForm.get(['noOfExecutors'])!.value,
      label: this.editForm.get(['label'])!.value,
      workspace: this.editForm.get(['workspace'])!.value,
      dnsName: this.editForm.get(['dnsName'])!.value,
      agentState: this.editForm.get(['agentState'])!.value,
      bootstrapMetod: this.editForm.get(['bootstrapMetod'])!.value,
      os: this.editForm.get(['os'])!.value,
      sshPort: this.editForm.get(['sshPort'])!.value,
      jvmOptions: this.editForm.get(['jvmOptions'])!.value,
      sshCredentials: this.editForm.get(['sshCredentials'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISharedAgent>>): void {
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

  trackById(index: number, item: ISshCredentials): any {
    return item.id;
  }
}
