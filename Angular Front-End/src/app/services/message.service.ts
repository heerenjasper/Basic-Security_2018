import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

const BASE_API_URL = 'localhost:8081/api/files';

@Injectable()
export class MessageService {

  constructor(
    private httpClient: HttpClient
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

}
