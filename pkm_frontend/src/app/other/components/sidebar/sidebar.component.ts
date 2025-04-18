import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {FoldersService} from '../../../../../libs/api';
import {Router} from '@angular/router';

@Component({
  selector: 'sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  @Input() selectedFolder: string = '';
  @Output() folderSelected: EventEmitter<string> = new EventEmitter();

  folders: string[] = [];
  folderNames: Map<string, string> = new Map();

  private router: Router = inject(Router);
  private folderService: FoldersService = inject(FoldersService);

  ngOnInit(): void {
    this.loadFolders();
  }

  loadFolders(): void {
    this.folderService.getFolders().subscribe(folders => {
      folders.forEach(folder => {
        this.folderNames.set(folder.id, folder.name);
      });
      this.folders = Array.from(this.folderNames.values()).sort((a, b) =>
        a.localeCompare(b)
      );
    });
  }

  onFolderClick(folder: string): void {
    this.selectedFolder = folder;
    this.folderSelected.emit(folder);

    if (this.selectedFolder === "Todo") {
      this.router.navigate(['/todo']);
    }
  }
}
