export * from './folders.service';
import {FoldersService} from './folders.service';
import {NotesService} from './notes.service';

export * from './notes.service';
export const APIS = [FoldersService, NotesService];
