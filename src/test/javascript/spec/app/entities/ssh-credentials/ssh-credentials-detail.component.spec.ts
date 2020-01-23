import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JeevesdemoTestModule } from '../../../test.module';
import { SshCredentialsDetailComponent } from 'app/entities/ssh-credentials/ssh-credentials-detail.component';
import { SshCredentials } from 'app/shared/model/ssh-credentials.model';

describe('Component Tests', () => {
  describe('SshCredentials Management Detail Component', () => {
    let comp: SshCredentialsDetailComponent;
    let fixture: ComponentFixture<SshCredentialsDetailComponent>;
    const route = ({ data: of({ sshCredentials: new SshCredentials(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JeevesdemoTestModule],
        declarations: [SshCredentialsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SshCredentialsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SshCredentialsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sshCredentials on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sshCredentials).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
