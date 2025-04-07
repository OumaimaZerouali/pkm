import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class NoteService {

  apiUrl = 'http://localhost:8080/api/notes';

  constructor(private http: HttpClient) {}

  private createAuthHeader(): HttpHeaders {
    const username = 'pkm';
    const password = 'joplin-pkm';
    return new HttpHeaders({
      Authorization: 'Basic ' + btoa(username + ':' + password)
    });
  }

  getNotes(): Observable<any[]> {
    const headers = this.createAuthHeader();
    return this.http.get<any[]>(this.apiUrl, { headers });
  }

  getNoteById(noteId: string) {
    const headers = this.createAuthHeader();
    const url = `${this.apiUrl}/${noteId}`;
    return this.http.get<any>(url, { headers });
  }
}
