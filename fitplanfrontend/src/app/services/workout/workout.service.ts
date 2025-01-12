import { Injectable } from '@angular/core';
import {Exercise} from "../../model/exercise";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Workout} from "../../model/workout";

@Injectable({
  providedIn: 'root'
})
export class WorkoutService {

  constructor(private httpClient: HttpClient) {
  }

  createWorkout(workout: Workout): Observable<string> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'text' as 'json'
    };
    return this.httpClient.post<string>('http://localhost:9000/api/workout', workout, httpOptions);
  }
}
