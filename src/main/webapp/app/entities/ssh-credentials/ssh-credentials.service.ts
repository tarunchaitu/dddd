import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISshCredentials } from 'app/shared/model/ssh-credentials.model';

type EntityResponseType = HttpResponse<ISshCredentials>;
type EntityArrayResponseType = HttpResponse<ISshCredentials[]>;

@Injectable({ providedIn: 'root' })
export class SshCredentialsService {
  public resourceUrl = SERVER_API_URL + 'api/ssh-credentials';

  constructor(protected http: HttpClient) {}

  create(sshCredentials: ISshCredentials): Observable<EntityResponseType> {
    return this.http.post<ISshCredentials>(this.resourceUrl, sshCredentials, { observe: 'response' });
  }

  update(sshCredentials: ISshCredentials): Observable<EntityResponseType> {
    return this.http.put<ISshCredentials>(this.resourceUrl, sshCredentials, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISshCredentials>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISshCredentials[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
