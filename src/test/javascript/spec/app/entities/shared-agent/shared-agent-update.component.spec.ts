import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JeevesdemoTestModule } from '../../../test.module';
import { SharedAgentUpdateComponent } from 'app/entities/shared-agent/shared-agent-update.component';
import { SharedAgentService } from 'app/entities/shared-agent/shared-agent.service';
import { SharedAgent } from 'app/shared/model/shared-agent.model';

describe('Component Tests', () => {
  describe('SharedAgent Management Update Component', () => {
    let comp: SharedAgentUpdateComponent;
    let fixture: ComponentFixture<SharedAgentUpdateComponent>;
    let service: SharedAgentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JeevesdemoTestModule],
        declarations: [SharedAgentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SharedAgentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SharedAgentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SharedAgentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SharedAgent(123);
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
        const entity = new SharedAgent();
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
