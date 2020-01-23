import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISharedAgent } from 'app/shared/model/shared-agent.model';

@Component({
  selector: 'jhi-shared-agent-detail',
  templateUrl: './shared-agent-detail.component.html'
})
export class SharedAgentDetailComponent implements OnInit {
  sharedAgent: ISharedAgent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sharedAgent }) => {
      this.sharedAgent = sharedAgent;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
