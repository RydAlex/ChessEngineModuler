import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClusterPlayModeComponent } from './cluster-play-mode.component';

describe('ClusterPlayModeComponent', () => {
  let component: ClusterPlayModeComponent;
  let fixture: ComponentFixture<ClusterPlayModeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClusterPlayModeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClusterPlayModeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
