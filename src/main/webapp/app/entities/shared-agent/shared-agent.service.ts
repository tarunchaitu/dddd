import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISharedAgent } from 'app/shared/model/shared-agent.model';

type EntityResponseType = HttpResponse<ISharedAgent>;
type EntityArrayResponseType = HttpResponse<ISharedAgent[]>;

@Injectable({ providedIn: 'root' })
export class SharedAgentService {
  public resourceUrl = SERVER_API_URL + 'api/shared-agents';

  constructor(protected http: HttpClient) {}

  create(sharedAgent: ISharedAgent): Observable<EntityResponseType> {
    return this.http.post<ISharedAgent>(this.resourceUrl, sharedAgent, { observe: 'response' });
  }

  update(sharedAgent: ISharedAgent): Observable<EntityResponseType> {
    return this.http.put<ISharedAgent>(this.resourceUrl, sharedAgent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISharedAgent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISharedAgent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
