import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddExerciseComponent } from './add-exercise.component';

describe('AddProductComponent', () => {
  let component: AddExerciseComponent;
  let fixture: ComponentFixture<AddExerciseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddExerciseComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddExerciseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
