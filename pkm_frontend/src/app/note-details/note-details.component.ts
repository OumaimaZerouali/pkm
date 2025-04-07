import {Component, OnInit} from '@angular/core';
import {NoteService} from '../note/note.service';
import {ActivatedRoute} from '@angular/router';
import {MarkdownModule, MarkdownPipe, MarkdownService} from 'ngx-markdown';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-note-details',
  templateUrl: './note-details.component.html',
  imports: [
    MarkdownModule,
    DatePipe
  ],
  styleUrls: ['./note-details.component.scss']
})
export class NoteDetailsComponent implements OnInit {
  note: any;

  constructor(
    private noteService: NoteService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit() {
    const noteId = this.route.snapshot.paramMap.get('id');
    if (noteId) {
      this.noteService.getNoteById(noteId).subscribe(note => {
        this.note = note;
      });
    }
  }
}
