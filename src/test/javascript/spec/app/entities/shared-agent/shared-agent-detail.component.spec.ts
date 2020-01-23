import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JeevesdemoTestModule } from '../../../test.module';
import { SharedAgentDetailComponent } from 'app/entities/shared-agent/shared-agent-detail.component';
import { SharedAgent } from 'app/shared/model/shared-agent.model';

describe('Component Tests', () => {
  describe('SharedAgent Management Detail Component', () => {
    let comp: SharedAgentDetailComponent;
    let fixture: ComponentFixture<SharedAgentDetailComponent>;
    const route = ({ data: of({ sharedAgent: new SharedAgent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JeevesdemoTestModule],
        declarations: [SharedAgentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SharedAgentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SharedAgentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sharedAgent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sharedAgent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
