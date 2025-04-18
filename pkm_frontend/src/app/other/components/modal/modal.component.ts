import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'modal',
  standalone: true,
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent {
  @Output() closed = new EventEmitter<void>();

  close() {
    this.closed.emit();
  }

  onBackdropClick(event: MouseEvent) {
    this.close();
  }
}
