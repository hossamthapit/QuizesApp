import { Directive, ElementRef, Input, Renderer2, SimpleChanges } from '@angular/core';
import { AbstractControl, FormControl, NgControl } from '@angular/forms';

@Directive({
  selector: '[inputRequired]',
  standalone: true
})
export class InputRequiredDirective {

  @Input() inputRequired!: AbstractControl;

  constructor(private el: ElementRef, private renderer: Renderer2) { }

  ngOnChanges(changes: SimpleChanges): void {
    if ('inputRequired' in changes && this.inputRequired) {
      this.inputRequired.statusChanges.subscribe(() => {
        this.toggleClass(this.inputRequired);
      });
    }
  }

  private toggleClass(control: AbstractControl): void {
    const invalid = control.invalid && (control.dirty || control.touched);
    this.renderer.removeClass(this.el.nativeElement, 'is-invalid');
    if (invalid) {
      this.renderer.addClass(this.el.nativeElement, 'is-invalid');
    }
  }
}
