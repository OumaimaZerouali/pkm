import {Component, inject, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {DatePipe, Location} from '@angular/common';
import {MarkdownModule} from 'ngx-markdown';
import {FoldersService, NotesService} from '../../../libs/api';
import {FormsModule} from '@angular/forms';
import {MarkdownGuideComponent} from '../other/components/markdown-guide/markdown-guide.component';

@Component({
  selector: 'app-note-details',
  templateUrl: './note-details.component.html',
  styleUrls: ['./note-details.component.scss'],
  standalone: true,
  imports: [MarkdownModule, DatePipe, FormsModule, MarkdownGuideComponent],
})
export class NoteDetailsComponent implements OnInit {
  @ViewChild('markdownDialog') markdownDialog!: HTMLDialogElement;

  note: any;
  folder: any;
  editMode = false;

  private noteService: NotesService = inject(NotesService);
  private folderService: FoldersService = inject(FoldersService);
  private route: ActivatedRoute = inject(ActivatedRoute);
  private location: Location = inject(Location);

  ngOnInit() {
    const noteId = this.route.snapshot.paramMap.get('id');
    if (noteId) {
      this.noteService.getNoteById(noteId).subscribe((note) => {
        this.note = note;
        this.folderService.getFolderById(note.folder!).subscribe((folder: any) => {
          this.folder = folder;
        });
      });
    }
  }

  goBack(): void {
    this.location.back();
  }

  deleteNote(): void {
    if (confirm('Are you sure you want to delete this note?')) {
      this.noteService.deleteNoteById(this.note.id).subscribe(() => {
        this.goBack();
      });
    }
  }

  cancelEdit(): void {
    this.editMode = false;
    this.ngOnInit();
  }

  updateNote(): void {
    this.noteService.createOrUpdateNote(this.note.id, this.note).subscribe((updatedNote) => {
      this.note = updatedNote;
      this.editMode = false;
    });
  }
}
