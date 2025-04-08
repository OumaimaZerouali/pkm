import {Component, inject, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {FoldersService, NotesService} from '../../../libs/api';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  imports: [
    FormsModule,
    RouterLink
  ],
  styleUrls: ['./note.component.scss']
})
export class NoteComponent implements OnInit {
  notes: any[] = [];
  originalNotes: any[] = [];
  folderNames: Map<string, string> = new Map();
  folders: string[] = [];
  selectedFolder: string = '';

  private noteService: NotesService = inject(NotesService);
  private folderService: FoldersService = inject(FoldersService);

  ngOnInit() {
    this.loadNotes();
  }

  loadNotes() {
    this.noteService.getNotes().subscribe(notes => {
      this.originalNotes = notes;
      this.notes = [...notes];
      this.loadFolderNames();
    });
  }

  loadFolderNames() {
    const folderIds = new Set<string>();

    this.notes.forEach(note => {
      if (note.folder && !this.folderNames.has(note.folder)) {
        folderIds.add(note.folder);
      }
    });

    folderIds.forEach(folderId => {
      this.folderService.getFolderById(folderId).subscribe(folder => {
        this.folderNames.set(folderId, folder.name);
        this.updateFoldersDropdown();
      });
    });
  }

  updateFoldersDropdown() {
    this.folders = Array.from(this.folderNames.values());
  }

  getFolderName(folderId: string): string {
    return this.folderNames.get(folderId) || 'Unknown Folder';
  }

  filterNotes() {
    if (this.selectedFolder) {
      this.notes = this.originalNotes.filter(note => this.getFolderName(note.folder) === this.selectedFolder);
    } else {
      this.notes = [...this.originalNotes];
    }
  }
}
