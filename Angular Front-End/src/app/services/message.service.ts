import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

const BASE_API_URL = 'http://localhost:8081/api/files';

@Injectable()
export class MessageService {

  constructor(
    private httpClient: HttpClient,
    private http: Http
  ) {}

   postMessage(message: string, file: File): Observable<HttpEvent<{}>> {
      const formData: FormData = new FormData();

      formData.append('file', file);
      formData.append('message', new Blob([JSON.stringify(message)],
        {
          type: 'application/json'
        }));

      const req = new HttpRequest('POST', BASE_API_URL + '/', formData, {
          reportProgress: true,
          responseType: 'text'
      });

      return this.httpClient.request(req);
   }

   
   encryptMessage(message: string, file: File): Observable<HttpEvent<{}>> {
    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('message', new Blob([JSON.stringify(message)],
      {
        type: 'application/json'
      }));

    const req = new HttpRequest('POST', BASE_API_URL + '/encrypt/', formData, {
        reportProgress: true,
        responseType: 'text'
    });

    return this.httpClient.request(req);
 }

 decryptMessage(fileId: number) {
   return this.http.get(BASE_API_URL + '/decrypt/' + fileId)
    .catch(this.onError);
 }

 /*
   onError is the global method to catch errors when creating observables
 */
 onError(res: Response): Observable<any> {
   const error = `Error ${res.status}: ${res.statusText}`;
   console.error(error);
   return Observable.throw(error);
 }
}
