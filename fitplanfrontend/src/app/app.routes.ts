import { Routes } from '@angular/router';
import {HomePageComponent} from "./pages/home-page/home-page.component";
import {AddExerciseComponent} from "./pages/add-exercise/add-exercise.component";

export const routes: Routes = [
  {path: '', component: HomePageComponent},
  {path: 'add-exercise', component: AddExerciseComponent}
];
