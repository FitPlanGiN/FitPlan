export interface Workout {
  id?: number;
  name: string;
  description: string;
  userDetails: UserDetails
}

export interface UserDetails {
  email: string;
  firstName: string;
  lastName: string;
}
