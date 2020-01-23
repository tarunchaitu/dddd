import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISharedAgent } from 'app/shared/model/shared-agent.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SharedAgentService } from './shared-agent.service';
import { SharedAgentDeleteDialogComponent } from './shared-agent-delete-dialog.component';

@Component({
  selector: 'jhi-shared-agent',
  templateUrl: './shared-agent.component.html'
})
export class SharedAgentComponent implements OnInit, OnDestroy {
  sharedAgents: ISharedAgent[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sharedAgentService: SharedAgentService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sharedAgents = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sharedAgentService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISharedAgent[]>) => this.paginateSharedAgents(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.sharedAgents = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSharedAgents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISharedAgent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSharedAgents(): void {
    this.eventSubscriber = this.eventManager.subscribe('sharedAgentListModification', () => this.reset());
  }

  delete(sharedAgent: ISharedAgent): void {
    const modalRef = this.modalService.open(SharedAgentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sharedAgent = sharedAgent;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSharedAgents(data: ISharedAgent[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sharedAgents.push(data[i]);
      }
    }
  }
}
