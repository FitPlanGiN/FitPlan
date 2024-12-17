import {Component, inject, OnInit} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {Exercise} from "../../model/exercise";
import {ExerciseService} from "../../services/exercise/exercise.service";
import {AsyncPipe, JsonPipe} from "@angular/common";
import {Router} from "@angular/router";
import {Workout} from "../../model/workout";
import {FormsModule} from "@angular/forms";
import {WorkoutService} from "../../services/workout/workout.service";

@Component({
  selector: 'app-homepage',
  templateUrl: './home-page.component.html',
  standalone: true,
  imports: [
    AsyncPipe,
    JsonPipe,
    FormsModule
  ],
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit {
  private readonly oidcSecurityService = inject(OidcSecurityService);
  private readonly exerciseService = inject(ExerciseService);
  private readonly workoutService = inject(WorkoutService);
  private readonly router = inject(Router);
  isAuthenticated = false;
  exercises: Array<Exercise> = [];
  quantityIsNull = false;
  createSuccess = false;
  createFail = false;

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({isAuthenticated}) => {
        this.isAuthenticated = isAuthenticated;
        this.exerciseService.getExercises()
          .pipe()
          .subscribe(exercise => {
            this.exercises = exercise;
          })
      }
    )
  }

  goToCreateExercisePage() {
    this.router.navigateByUrl('/add-exercise');
  }

  createExercise(exercise: Exercise, quantity: string) {

    this.oidcSecurityService.userData$.subscribe(result => {
      const userDetails = {
        email: result.userData.email,
        firstName: result.userData.firstName,
        lastName: result.userData.lastName
      };

      if(!quantity) {
        this.createFail = true;
        this.createSuccess = false;
        this.quantityIsNull = true;
      } else {
        const workout: Workout = {
          name: exercise.name,
          description: exercise.description,
          userDetails: userDetails
        }

        this.workoutService.createWorkout(workout).subscribe(() => {
          this.createSuccess = true;
        }, error => {
          this.createFail = false;
        })
      }
    })
  }
}
