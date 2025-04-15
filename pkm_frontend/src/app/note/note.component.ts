import {Component, inject, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {DatePipe} from '@angular/common';

import {FoldersService, NotesService} from '../../../libs/api';
import {SidebarComponent} from '../other/components/sidebar/sidebar.component';
import {ModalComponent} from '../other/components/modal/modal.component';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.scss'],
  standalone: true,
  imports: [
    FormsModule,
    RouterLink,
    SidebarComponent,
    DatePipe,
    ModalComponent,
  ]
})
export class NoteComponent implements OnInit, OnChanges {
  notes: any[] = [];
  folders: string[] = [];
  newFolderName = '';
  selectedFolder = '';
  originalNotes: any[] = [];
  creatingNote = false;
  creatingFolder = false;
  folderNames = new Map<string, string>();
  newNote = this.emptyNote();

  private readonly noteService = inject(NotesService);
  private readonly folderService = inject(FoldersService);
  private readonly router = inject(Router);

  ngOnInit(): void {
    this.loadNotes();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.loadNotes();
  }

  openNoteModal() { this.creatingNote = true; }
  closeNoteModal() { this.creatingNote = false; }

  openFolderModal() { this.creatingFolder = true; }
  closeFolderModal() { this.creatingFolder = false; }

  createFolder(): void {
    const folder = {
      id: this.generateHexId(),
      name: this.newFolderName,
      icon: ''
    };

    this.folderService.createOrUpdateFolder(folder).subscribe(() => {
      this.folderNames.set(folder.id, folder.name);
      this.newFolderName = '';
      this.closeFolderModal();
    });
  }

  createNote(): void {
    const id = this.generateHexId();
    const folderName = this.folderNames.get(this.newNote.folder);

    this.folderService.getFolders(folderName).subscribe(folderId => {
      if (!folderId || folderId.length === 0) {
        console.error('Folder not found!');
        return;
      }

      this.folderService.getFolderById(folderId[0].id).subscribe(folder => {
        this.folderNames.set(folder.id, folder.name);

        this.newNote.folder = folder.id;

        const payload = {
          id,
          ...this.newNote,
          created: new Date().toISOString(),
          updated: new Date().toISOString()
        };

        this.noteService.createOrUpdateNote(payload).subscribe(() => {
          this.creatingNote = false;
          this.newNote = this.emptyNote();
          this.updateFolderList();
          this.loadNotes();

          this.router.navigate(['/notes', id]);
        });
      });
    });
  }

  goToChatbotPage(): void {
    this.router.navigate(['/chat']);
  }

  onFolderSelected(folder: string): void {
    this.selectedFolder = folder;
    this.filterNotesByFolder();
  }

  getFolderName(folderId: string): string {
    return this.folderNames.get(folderId) || 'Unknown Folder';
  }

  protected loadNotes(): void {
    this.noteService.getNotes().subscribe(notes => {
      notes.sort((a, b) =>
        new Date(b.updated ?? b.created ?? 0).getTime() -
        new Date(a.updated ?? a.created ?? 0).getTime()
      );

      this.originalNotes = notes;
      this.notes = [...notes];
      this.loadFolderNames();
    });
  }


  private loadFolderNames(): void {
    const folderIds = new Set<string>(
      this.notes
        .map(note => note.folder)
        .filter(folder => folder && !this.folderNames.has(folder))
    );

    folderIds.forEach(folderId => {
      this.folderService.getFolderById(folderId).subscribe(folder => {
        this.folderNames.set(folderId, folder.name);
        this.updateFolderList();
      });
    });
  }

  private updateFolderList(): void {
    this.folders = Array.from(this.folderNames.values()).sort((a, b) =>
      a.localeCompare(b)
    );
  }

  private filterNotesByFolder(): void {
    this.notes = this.selectedFolder
      ? this.originalNotes.filter(
        note => this.getFolderName(note.folder) === this.selectedFolder
      )
      : [...this.originalNotes];
  }

  private emptyNote() {
    return {
      title: '',
      content: '',
      author: '',
      folder: ''
    };
  }

  private generateHexId(): string {
    const array = new Uint8Array(16);
    crypto.getRandomValues(array);
    return Array.from(array)
      .map(b => b.toString(16).padStart(2, '0'))
      .join('');
  }
}
