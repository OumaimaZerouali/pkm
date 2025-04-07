import { Routes } from '@angular/router';
import {NoteComponent} from './note/note.component';
import {NoteDetailsComponent} from './note-details/note-details.component';

export const routes: Routes = [
  { path: 'notes', component: NoteComponent },
  { path: 'notes/:id', component: NoteDetailsComponent },
  { path: '', redirectTo: '/notes', pathMatch: 'full' }
];
