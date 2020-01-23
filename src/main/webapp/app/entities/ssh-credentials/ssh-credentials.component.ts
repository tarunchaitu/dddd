import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISshCredentials } from 'app/shared/model/ssh-credentials.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SshCredentialsService } from './ssh-credentials.service';
import { SshCredentialsDeleteDialogComponent } from './ssh-credentials-delete-dialog.component';

@Component({
  selector: 'jhi-ssh-credentials',
  templateUrl: './ssh-credentials.component.html'
})
export class SshCredentialsComponent implements OnInit, OnDestroy {
  sshCredentials: ISshCredentials[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sshCredentialsService: SshCredentialsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sshCredentials = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sshCredentialsService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISshCredentials[]>) => this.paginateSshCredentials(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.sshCredentials = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSshCredentials();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISshCredentials): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSshCredentials(): void {
    this.eventSubscriber = this.eventManager.subscribe('sshCredentialsListModification', () => this.reset());
  }

  delete(sshCredentials: ISshCredentials): void {
    const modalRef = this.modalService.open(SshCredentialsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sshCredentials = sshCredentials;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSshCredentials(data: ISshCredentials[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sshCredentials.push(data[i]);
      }
    }
  }
}
