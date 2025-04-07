import { Component, OnInit } from '@angular/core';
import { NoteService } from './note.service';
import { FolderService } from './folder.service';
import { FormsModule } from '@angular/forms';
import {RouterLink} from '@angular/router';

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

  constructor(
    private noteService: NoteService,
    private folderService: FolderService
  ) { }

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
