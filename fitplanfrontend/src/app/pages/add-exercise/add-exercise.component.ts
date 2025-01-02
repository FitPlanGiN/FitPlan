import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Exercise} from "../../model/exercise";
import {ExerciseService} from "../../services/exercise/exercise.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-add-exercise',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './add-exercise.component.html',
  styleUrl: './add-exercise.component.css'
})
export class AddExerciseComponent {
  addExerciseForm: FormGroup;
  private readonly exerciseService = inject(ExerciseService);
  exerciseCreated = false;

  constructor(private fb: FormBuilder) {
    this.addExerciseForm = this.fb.group({
      //id: ['', [Validators.required]],
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      category: ['', [Validators.required]]
    })
  }

  onSubmit(): void {
    if (this.addExerciseForm.valid) {
      const exercise: Exercise = {
        //id: this.addExerciseForm.get('id')?.value,
        name: this.addExerciseForm.get('name')?.value,
        description: this.addExerciseForm.get('description')?.value,
        category: this.addExerciseForm.get('category')?.value
      }
      this.exerciseService.createExercise(exercise).subscribe(exercise => {
        this.exerciseCreated = true;
        this.addExerciseForm.reset();
      })
    } else {
      console.log('Form is not valid');
    }
  }

  get id() {
    return this.addExerciseForm.get('id');
  }

  get name() {
    return this.addExerciseForm.get('name');
  }

  get description() {
    return this.addExerciseForm.get('description');
  }

  get category() {
    return this.addExerciseForm.get('category');
  }
}
