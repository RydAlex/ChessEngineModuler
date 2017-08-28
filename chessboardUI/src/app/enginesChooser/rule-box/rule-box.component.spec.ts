import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RuleBoxComponent } from './rule-box.component';

describe('RuleBoxComponent', () => {
  let component: RuleBoxComponent;
  let fixture: ComponentFixture<RuleBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RuleBoxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RuleBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
