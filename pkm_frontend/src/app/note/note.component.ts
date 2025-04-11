import {Component, inject, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {FoldersService, NotesService} from '../../../libs/api';
import {SidebarComponent} from '../other/components/sidebar/sidebar.component';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  imports: [
    FormsModule,
    RouterLink,
    SidebarComponent
  ],
  styleUrls: ['./note.component.scss']
})
export class NoteComponent implements OnInit {
  notes: any[] = [];
  originalNotes: any[] = [];
  folderNames: Map<string, string> = new Map();
  folders: string[] = [];
  selectedFolder: string = '';
  creatingNote = false;

  newNote = {
    title: '',
    content: '',
    author: '',
    folder: ''
  };

  private noteService: NotesService = inject(NotesService);
  private folderService: FoldersService = inject(FoldersService);
  private router: Router = inject(Router);

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
        this.updateFolders();
      });
    });
  }

  updateFolders() {
    this.folders = Array.from(this.folderNames.values()).sort((a, b) =>
      a.localeCompare(b)
    );
  }

  getFolderName(folderId: string): string {
    return this.folderNames.get(folderId) || 'Unknown Folder';
  }

  filterNotesByFolder() {
    if (this.selectedFolder) {
      this.notes = this.originalNotes.filter(note => this.getFolderName(note.folder) === this.selectedFolder);
    } else {
      this.notes = [...this.originalNotes];
    }
  }

  onFolderSelected(folder: string): void {
    this.selectedFolder = folder;
    this.filterNotesByFolder();
  }

  createNote() {
    const id = this.generateHexId();

    const payload = {
      id,
      ...this.newNote,
      created: new Date().toISOString(),
      updated: new Date().toISOString()
    };

    this.noteService.createOrUpdateNote(payload).subscribe(() => {
      this.creatingNote = false;
      this.resetNewNote();
      this.loadNotes();
    });
  }

  resetNewNote() {
    this.newNote = {
      title: '',
      content: '',
      author: '',
      folder: ''
    };
  }

  goToChatbotPage() {
    this.router.navigate(['/chat']);
  }

  private generateHexId(): string {
    const array = new Uint8Array(16);
    crypto.getRandomValues(array);
    return Array.from(array)
      .map(b => b.toString(16).padStart(2, '0'))
      .join('');
  }
}
