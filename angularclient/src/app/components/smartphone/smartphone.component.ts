import { Component } from '@angular/core';
import { SmartphoneService } from '../../services/smartphone.service';
import { Smartphone } from '../../model/smartphone';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-smartphone',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './smartphone.component.html',
  styleUrls: ['./smartphone.component.scss']
})
export class SmartphoneComponent {
  // Formularfelder Registrierung
  regDeviceId = '';
  regUserName = '';
  regSecretHash = '';

  // Formularfelder Auth
  authDeviceId = '';
  authUserName = '';
  authSecretHash = '';
  authToken: string | null = null;

  // Liste
  phones: Smartphone[] = [];
  loading = false;
  errorMsg = '';

  constructor(private smartphoneService: SmartphoneService) {
    this.loadList();
  }

  loadList(): void {
    this.loading = true;
    this.smartphoneService.getAll().subscribe({
      next: data => { this.phones = data; this.loading = false; },
      error: err => { this.errorMsg = err.message || 'Fehler beim Laden'; this.loading = false; }
    });
  }

  register(): void {
    const body: Smartphone = {
      deviceId: this.regDeviceId.trim(),
      userName: this.regUserName.trim(),
      secretHash: this.regSecretHash.trim()
    };
    if (!body.deviceId || !body.userName || !body.secretHash) { alert('Bitte alle Felder ausfüllen'); return; }

    this.smartphoneService.registerSmartphone(body).subscribe({
      next: _ => { this.clearRegForm(); this.loadList(); alert('Registrierung erfolgreich'); },
      error: err => { this.errorMsg = err.error?.message || 'Registrierung fehlgeschlagen'; }
    });
  }

  requestToken(): void {
    const did = this.authDeviceId.trim();
    const us  = this.authUserName.trim();
    const sh  = this.authSecretHash.trim();
    if (!did || !us || !sh) { alert('Bitte alle Felder ausfüllen'); return; }

    this.smartphoneService.requestToken(did, us, sh).subscribe({
      next: res => { this.authToken = res.auth_token; alert('Token erhalten'); this.clearAuthForm(); },
      error: _ => { this.authToken = null; alert('Auth fehlgeschlagen'); }
    });
  }

  clearRegForm(): void {
    this.regDeviceId = ''; this.regUserName = ''; this.regSecretHash = '';
  }

  clearAuthForm(): void {
    this.authDeviceId = '';
    this.authUserName = '';
    this.authSecretHash = '';
    // this.authToken = null;
  }
}

