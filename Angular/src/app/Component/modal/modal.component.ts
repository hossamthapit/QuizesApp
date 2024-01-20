import { Component } from '@angular/core';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [],
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.css'
})
export class ModalComponent {
  
  title: string | null = null;
  description : string | null = null;
  
  constructor(public modalRef: MdbModalRef<ModalComponent>) {}
  
  close(): void {
    this.modalRef.close(false);
  }
  
  confirm() {
    this.modalRef.close(true);
  }
}
