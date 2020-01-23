import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { SharedAgentService } from 'app/entities/shared-agent/shared-agent.service';
import { ISharedAgent, SharedAgent } from 'app/shared/model/shared-agent.model';
import { AgentState } from 'app/shared/model/enumerations/agent-state.model';
import { BootstrapMethod } from 'app/shared/model/enumerations/bootstrap-method.model';
import { Os } from 'app/shared/model/enumerations/os.model';

describe('Service Tests', () => {
  describe('SharedAgent Service', () => {
    let injector: TestBed;
    let service: SharedAgentService;
    let httpMock: HttpTestingController;
    let elemDefault: ISharedAgent;
    let expectedResult: ISharedAgent | ISharedAgent[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SharedAgentService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SharedAgent(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        AgentState.ONLINE,
        BootstrapMethod.SSH,
        Os.WINDOWS,
        0,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SharedAgent', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new SharedAgent())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SharedAgent', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            noOfExecutors: 1,
            label: 'BBBBBB',
            workspace: 'BBBBBB',
            dnsName: 'BBBBBB',
            agentState: 'BBBBBB',
            bootstrapMetod: 'BBBBBB',
            os: 'BBBBBB',
            sshPort: 1,
            jvmOptions: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SharedAgent', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            noOfExecutors: 1,
            label: 'BBBBBB',
            workspace: 'BBBBBB',
            dnsName: 'BBBBBB',
            agentState: 'BBBBBB',
            bootstrapMetod: 'BBBBBB',
            os: 'BBBBBB',
            sshPort: 1,
            jvmOptions: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SharedAgent', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
