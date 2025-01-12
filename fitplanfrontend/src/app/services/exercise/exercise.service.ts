import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { Exercise } from '../../model/exercise';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  constructor(private httpClient: HttpClient) {
  }

  private clusterUrl = 'http://gateway.default.svc.cluster.local:9000/api/exercise';
  private localUrl = 'http://localhost:9000/api/exercise';

  /*
  getExercises(): Observable<Array<Exercise>> {
    return this.httpClient.get<Array<Exercise>>('http://gateway.default.svc.cluster.local:9000/api/exercise');
  }

  createExercise(exercise: Exercise): Observable<Exercise> {
    return this.httpClient.post<Exercise>('http://gateway.default.svc.cluster.local:9000/api/exercise', exercise);
  }*/

  /*
  getExercises(): Observable<Array<Exercise>> {
    return this.httpClient.get<Array<Exercise>>('http://34.118.122.132:31823/api/exercise');
  }

  createExercise(exercise: Exercise): Observable<Exercise> {
    return this.httpClient.post<Exercise>('http://34.118.122.132:31823/api/exercise', exercise);
  }*/

  /*getExercises(): Observable<Array<Exercise>> {
    return this.httpClient.get<Array<Exercise>>('http://localhost:9000/api/exercise');
  }

  createExercise(exercise: Exercise): Observable<Exercise> {
    return this.httpClient.post<Exercise>('http://localhost:9000/api/exercise', exercise);
  }*/

  getExercises(): Observable<Array<Exercise>> {
    return this.httpClient.get<Array<Exercise>>(this.clusterUrl).pipe(
      catchError(() => {
        console.warn('Failed to connect to cluster URL. Falling back to localhost...');
        return this.httpClient.get<Array<Exercise>>(this.localUrl);
      })
    );
  }

  createExercise(exercise: Exercise): Observable<Exercise> {
    return this.httpClient.post<Exercise>(this.clusterUrl, exercise).pipe(
      catchError(() => {
        console.warn('Failed to connect to cluster URL. Falling back to localhost...');
        return this.httpClient.post<Exercise>(this.localUrl, exercise);
      })
    );
  }
}
