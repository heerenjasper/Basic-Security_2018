import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MDBBootstrapModule } from './typescripts/free';
import { AgmCoreModule } from '@agm/core';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { AngularFireModule } from 'angularfire2';
import { environment } from '../environments/environment';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { appRoutes } from "../routes";
import { RouterModule } from "@angular/router";
import { NavbarComponent } from './navbar/navbar.component';
import { ChatComponent } from './chat/chat.component';
import { MessagesComponent } from './messages/messages.component';
import { ContactsComponent } from './contacts/contacts.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { MessageService } from './services/message.service';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    UserProfileComponent,
    NavbarComponent,
    ChatComponent,
    MessagesComponent,
    ContactsComponent,
    LoginComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    MDBBootstrapModule.forRoot(),
    AgmCoreModule.forRoot({
      // https://developers.google.com/maps/documentation/javascript/get-api-key?hl=en#key
      apiKey: 'Your_api_key'
    }),
    AngularFireModule.initializeApp(environment.firebase),
    CoreModule
  ],
  providers: [
    MessageService
  ],
  bootstrap: [AppComponent],
  schemas:      [ NO_ERRORS_SCHEMA ]
})
export class AppModule { }
