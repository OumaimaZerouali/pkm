import {render} from '@testing-library/angular';
import {MarkdownPipe} from './markdown.pipe';

describe('MarkdownPipe', () => {
  it('should transform markdown syntax to HTML', async () => {
    const { findByText } = await render('<div>{{ "**bold** text" | markdown }}</div>', {
      declarations: [MarkdownPipe],
    });

    const result = await findByText('<b>bold</b> text');
    expect(result).toBeDefined();
  });

  it('should handle lists', async () => {
    const { findByText } = await render('<div>{{ "1. First item" | markdown }}</div>', {
      declarations: [MarkdownPipe],
    });

    const result = await findByText('<ul><li>1. First item</li></ul>');
    expect(result).toBeDefined();
  });

  it('should handle new lines', async () => {
    const { findByText } = await render('<div>{{ "First line\nSecond line" | markdown }}</div>', {
      declarations: [MarkdownPipe],
    });

    const result = await findByText('<br>Second line');
    expect(result).toBeDefined();
  });
});
