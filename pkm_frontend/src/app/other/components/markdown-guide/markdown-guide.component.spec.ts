import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MarkdownGuideComponent} from './markdown-guide.component';

describe('MarkdownGuideComponent', () => {
  let component: MarkdownGuideComponent;
  let fixture: ComponentFixture<MarkdownGuideComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MarkdownGuideComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MarkdownGuideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
