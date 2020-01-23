import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JeevesdemoTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { SshCredentialsDeleteDialogComponent } from 'app/entities/ssh-credentials/ssh-credentials-delete-dialog.component';
import { SshCredentialsService } from 'app/entities/ssh-credentials/ssh-credentials.service';

describe('Component Tests', () => {
  describe('SshCredentials Management Delete Component', () => {
    let comp: SshCredentialsDeleteDialogComponent;
    let fixture: ComponentFixture<SshCredentialsDeleteDialogComponent>;
    let service: SshCredentialsService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JeevesdemoTestModule],
        declarations: [SshCredentialsDeleteDialogComponent]
      })
        .overrideTemplate(SshCredentialsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SshCredentialsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SshCredentialsService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
