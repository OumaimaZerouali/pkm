import {Component, inject} from '@angular/core';
import {AIRequest, AiService, ChatWithAI200Response, ChatWithAIRequest, FoldersService, Note} from '../../../libs/api';
import {FormsModule} from '@angular/forms';
import {MarkdownPipe} from '../other/markdown.pipe';
import {catchError, map, Observable, of, tap} from 'rxjs';

@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  imports: [FormsModule, MarkdownPipe],
  styleUrls: ['./chatbot.component.scss']
})
export class ChatbotComponent {
  messages: { user: string, ai: string }[] = [];
  currentMessage = '';
  isLoading = false;
  folderId: string | null = null;

  private ai: AiService = inject(AiService);
  private folderService: FoldersService = inject(FoldersService);

  sendMessage() {
    if (!this.currentMessage.trim()) return;

    const userMessage = this.currentMessage;
    this.currentMessage = '';
    this.isLoading = true;

    const requestPayload: ChatWithAIRequest = { message: userMessage };

    this.ai.chatWithAI(requestPayload).subscribe({
      next: (response: ChatWithAI200Response) => {
        this.messages.push({ user: userMessage, ai: response.response ?? 'No response from AI' });

        if (this.shouldCreateNote(userMessage)) {
          const folderName = this.extractFolderName(userMessage);
          if (folderName) {
            this.getFolderIdByName(folderName).subscribe({
              next: (folderId) => {
                if (folderId) {
                  this.folderId = folderId;
                  this.createNoteFromAI(response.response ?? 'No response from AI');
                } else {
                  console.log('Folder not found, asking the user for a folder name...');
                }
              },
              error: (err) => {
                console.error('Error fetching folder ID:', err);
              }
            });
          } else {
            this.folderId = 'b0a45b271cde4528ade82b40f3643774';
            this.createNoteFromAI(response.response ?? 'No response from AI');
          }
        }

        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error communicating with AI:', err);
        this.isLoading = false;
      }
    });
  }

  shouldCreateNote(userMessage: string): boolean {
    const createNoteKeywords = [
      'create a note', 'make a note', 'add a note', 'save this note',
      'jot this down', 'take a note', 'record this', 'write this down',
      'note this', 'create a reminder', 'add to my notes', 'save this information',
      'save this idea', 'add to my list', 'create a new note', 'write a new note',
      'save this entry', 'create a new reminder', 'create a note for me',
      'make a note of this', 'create a task', 'save this task', 'record this idea',
      'note it down', 'capture this', 'note down this', 'write this as a note',
      'create a memo', 'record this memo', 'add a memo', 'create a journal entry'
    ];
    return createNoteKeywords.some(keyword => userMessage.toLowerCase().includes(keyword));
  }

  extractFolderName(userMessage: string): string | null {
    const folderKeywords = ['in my', 'under', 'in the folder'];
    let folderName = null;

    folderKeywords.forEach(keyword => {
      const startIndex = userMessage.toLowerCase().indexOf(keyword);
      if (startIndex !== -1) {
        folderName = userMessage.substring(startIndex + keyword.length).trim();
      }
    });
    return folderName ? folderName : null;
  }

  createNoteFromAI(aiResponse: string) {
    const aiRequestPayload: AIRequest = {
      message: aiResponse,
      folder: this.folderId || "",
      createNoteIfPossible: true
    };

    this.ai.createNoteFromAI(aiRequestPayload).subscribe({
      next: (noteResponse: Note) => {
        console.log('Note created successfully:', noteResponse);
      },
      error: (err) => {
        console.error('Error creating note:', err);
      }
    });
  }

  getFolderIdByName(folderName: string): Observable<string | null> {
    return this.folderService.getFolders().pipe(
      tap(folders => console.log('Fetched folders:', folders)),
      map(folders => {
        console.log("Fetched folders:", folders);
        const folder = folders.find(f => f.name.toLowerCase() === folderName.toLowerCase());
        if (folder) {
          console.log("Found folder:", folder);
          return folder.id;
        } else {
          console.log("Folder not found.");
          return null;
        }
      }),
      catchError((err: any) => {
        console.error('Error fetching folders:', err);
        return of(null);
      })
    );
  }

}
