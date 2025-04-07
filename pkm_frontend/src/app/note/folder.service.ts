import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FolderService {

  apiUrl = 'http://localhost:8080/api/folders';

  constructor(private http: HttpClient) {}

  private createAuthHeader(): HttpHeaders {
    const username = 'pkm';
    const password = 'joplin-pkm';
    return new HttpHeaders({
      Authorization: 'Basic ' + btoa(username + ':' + password)
    });
  }

  getFolderById(folderId: string): Observable<any> {
    const headers = this.createAuthHeader();
    const url = `${this.apiUrl}/${folderId}`;
    return this.http.get<any>(url, { headers });
  }
}
