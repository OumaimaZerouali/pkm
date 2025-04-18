import {Component, inject, OnInit} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import {RouterLink} from '@angular/router';
import {NotesService} from '../../../libs/api';
import {ReactiveFormsModule} from '@angular/forms';
import {SidebarComponent} from '../other/components/sidebar/sidebar.component';

@Component({
  selector: 'todo',
  standalone: true,
  imports: [CommonModule, RouterLink, DatePipe, ReactiveFormsModule, SidebarComponent],
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.scss'],
})
export class TodoComponent implements OnInit {
  notes: any[] = [];
  todos: any[] = [];
  originalNotes: any[] = [];
  selectedFolder = '';
  folderNames = new Map<string, string>();

  private readonly noteService = inject(NotesService);

  ngOnInit(): void {
    this.noteService.getNotes().subscribe(notes => {
      this.todos = notes.filter(note => note.todo);
    });
  }

  onFolderSelected(folder: string): void {
    this.selectedFolder = folder;
    this.filterNotesByFolder();
  }

  getFolderName(folderId: string): string {
    return this.folderNames.get(folderId) || 'Unknown Folder';
  }

  private filterNotesByFolder(): void {
    this.notes = this.selectedFolder
      ? this.originalNotes.filter(
        note => this.getFolderName(note.folder) === this.selectedFolder
      )
      : [...this.originalNotes];
  }
}

