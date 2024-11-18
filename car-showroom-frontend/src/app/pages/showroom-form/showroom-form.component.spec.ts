import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowroomFormComponent } from './showroom-form.component';

describe('ShowroomFormComponent', () => {
  let component: ShowroomFormComponent;
  let fixture: ComponentFixture<ShowroomFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowroomFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowroomFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
