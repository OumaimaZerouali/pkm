import {Component, Injector} from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {HttpClient, HttpClientModule} from '@angular/common/http';

@Component({
    selector: 'app-root',
    imports: [RouterOutlet],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Personal Knowledge Manager';
}
