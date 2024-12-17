import {Component, inject, OnInit} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  private readonly oidcSecurityService = inject(OidcSecurityService);
  isAuthenticated = false;
  username = "";

  ngOnInit(): void {
    /*
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({isAuthenticated}) => {
        this.isAuthenticated = isAuthenticated;
        console.log("isAuthenticated: "+isAuthenticated)
      }
    )*/
    this.oidcSecurityService.userData$.subscribe(
      ({userData}) => {
        this.username = userData.preferred_username
      }
    )

    this.oidcSecurityService.checkAuth().subscribe(({ isAuthenticated, userData, accessToken, idToken, configId }) => {
      this.isAuthenticated = isAuthenticated;
      console.log("isAuthenticated: "+isAuthenticated)
    });
    /*
    this.oidcSecurityService.getIdToken().subscribe((token) => {
      console.log('Token:', token);
    });
*/
  }



  login(): void {
    this.oidcSecurityService.authorize();
  }

  logout(): void {
    this.oidcSecurityService
      .logoff()
      .subscribe((result) => console.log(result));
  }
}
