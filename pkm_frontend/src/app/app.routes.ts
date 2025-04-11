import {Routes} from '@angular/router';
import {NoteComponent} from './note/note.component';
import {NoteDetailsComponent} from './note-details/note-details.component';
import {ChatbotComponent} from './chatbot/chatbot.component';

export const routes: Routes = [
  { path: 'notes', component: NoteComponent },
  { path: 'notes/:id', component: NoteDetailsComponent },
  { path: 'chat', component: ChatbotComponent},
  { path: '', redirectTo: '/notes', pathMatch: 'full' }
];
