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
    if (!body.deviceId || !body.userName || !body.secretHash) { this.errorMsg = 'Alle Felder ausfüllen'; return; }

    this.smartphoneService.registerSmartphone(body).subscribe({
      next: _ => { this.clearRegForm(); this.loadList(); alert('Registrierung erfolgreich'); },
      error: err => { this.errorMsg = err.error?.message || 'Registrierung fehlgeschlagen'; }
    });
  }

  requestToken(): void {
    const did = this.authDeviceId.trim();
    const sh  = this.authSecretHash.trim();
    if (!did || !sh) { this.errorMsg = 'DeviceId und SecretHash erforderlich'; return; }

    this.smartphoneService.requestToken(did, sh).subscribe({
      next: res => { this.authToken = res.auth_token; alert('Token erhalten'); },
      error: _ => { this.authToken = null; this.errorMsg = 'Auth fehlgeschlagen'; }
    });
  }

  clearRegForm(): void {
    this.regDeviceId = ''; this.regUserName = ''; this.regSecretHash = '';
  }
}

