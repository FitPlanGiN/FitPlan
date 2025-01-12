import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Exercise} from "../../model/exercise";

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  constructor(private httpClient: HttpClient) {
  }


  getExercises(): Observable<Array<Exercise>> {
    return this.httpClient.get<Array<Exercise>>('http://localhost:9000/api/exercise');
  }

  createExercise(exercise: Exercise): Observable<Exercise> {
    return this.httpClient.post<Exercise>('http://localhost:9000/api/exercise', exercise);
  }
}
