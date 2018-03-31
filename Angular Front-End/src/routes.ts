import { Routes } from "@angular/router";
import { ChatComponent } from "app/chat/chat.component";
import { LoginComponent } from "./app/login/login.component";
import { RegistrationComponent } from "app/registration/registration.component";

export const appRoutes: Routes = [
    {path: "", component: LoginComponent},
    {path: "chat", component: ChatComponent},
    {path: "registration", component: RegistrationComponent}
]