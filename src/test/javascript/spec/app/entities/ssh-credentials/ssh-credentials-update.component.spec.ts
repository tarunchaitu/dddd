import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JeevesdemoTestModule } from '../../../test.module';
import { SshCredentialsUpdateComponent } from 'app/entities/ssh-credentials/ssh-credentials-update.component';
import { SshCredentialsService } from 'app/entities/ssh-credentials/ssh-credentials.service';
import { SshCredentials } from 'app/shared/model/ssh-credentials.model';

describe('Component Tests', () => {
  describe('SshCredentials Management Update Component', () => {
    let comp: SshCredentialsUpdateComponent;
    let fixture: ComponentFixture<SshCredentialsUpdateComponent>;
    let service: SshCredentialsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JeevesdemoTestModule],
        declarations: [SshCredentialsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SshCredentialsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SshCredentialsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SshCredentialsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SshCredentials(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SshCredentials();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
