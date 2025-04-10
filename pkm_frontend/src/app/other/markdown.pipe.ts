import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'markdown'
})
export class MarkdownPipe implements PipeTransform {

  transform(value: string): string {
    if (!value) {
      return value;
    }

    let formattedValue = value.replace(/\*\*(.*?)\*\*/g, '<b>$1</b>');

    formattedValue = formattedValue.replace(/^(\d+\..+)/gm, '<li>$1</li>');
    formattedValue = formattedValue.replace(/(<li>.+<\/li>)/g, '<ul>$1</ul>');
    formattedValue = formattedValue.replace(/\n/g, '<br>');

    return formattedValue;
  }
}
