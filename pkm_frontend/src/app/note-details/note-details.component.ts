import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MarkdownModule} from 'ngx-markdown';
import {DatePipe, Location} from '@angular/common';
import {FoldersService, NotesService} from '../../../libs/api';

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
  folder: any;

  private noteService: NotesService = inject(NotesService);
  private folderService: FoldersService = inject(FoldersService);
  private route: ActivatedRoute = inject(ActivatedRoute);
  private location: Location = inject(Location);

  ngOnInit() {
    const noteId = this.route.snapshot.paramMap.get('id');
    if (noteId) {
      this.noteService.getNoteById(noteId).subscribe(note => {
        this.note = note;

        this.folderService.getFolderById(note.folder!).subscribe(folder => {
          this.folder = folder;
        });
      });
    }
  }

  goBack(): void {
    this.location.back();
  }
}

