import { UserProfileComponent } from "app/user-profile/user-profile.component";
import { Routes } from "@angular/router";
import { ChatComponent } from "app/chat/chat.component";

export const appRoutes: Routes = [
    {path: "", component: UserProfileComponent},
    {path: "chat", component: ChatComponent}
]