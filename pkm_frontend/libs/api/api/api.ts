export * from './ai.service';
import {AiService} from './ai.service';
import {FoldersService} from './folders.service';
import {NotesService} from './notes.service';

export * from './folders.service';
export * from './notes.service';
export const APIS = [AiService, FoldersService, NotesService];
