import { Component } from '@angular/core';
import {
    RouterLink,
    RouterLinkActive,
    RouterOutlet
} from '@angular/router';

@Component({
    selector: 'app-shell',
    standalone: true,
    imports: [
        RouterOutlet,
        RouterLink,
        RouterLinkActive
    ],
    templateUrl: './app-shell.html',
    styleUrl: './app-shell.scss'
})
export class AppShellComponent {

    isSidebarOpen = false;

    toggleSidebar(): void {
        this.isSidebarOpen = !this.isSidebarOpen;
    }

    closeSidebar(): void {
        this.isSidebarOpen = false;
    }
}