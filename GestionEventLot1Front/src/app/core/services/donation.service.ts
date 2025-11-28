import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';
import {
  Donation,
  DonationCreatePayload,
  DonationStatus,
  MatchResult,
} from '../models/donation.model';

@Injectable({
  providedIn: 'root',
})
export class DonationService {
  private readonly baseUrl = `${environment.apiUrl}/donations`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Donation[]> {
    return this.http.get<Donation[]>(this.baseUrl);
  }

  getById(id: number): Observable<Donation> {
    return this.http.get<Donation>(`${this.baseUrl}/${id}`);
  }

  create(payload: DonationCreatePayload): Observable<Donation> {
    return this.http.post<Donation>(this.baseUrl, payload);
  }

  updateStatus(id: number, status: DonationStatus): Observable<Donation> {
    const params = new HttpParams().set('status', status);
    return this.http.patch<Donation>(`${this.baseUrl}/${id}/status`, null, {
      params,
    });
  }

  matchDonation(id: number): Observable<MatchResult> {
    return this.http.post<MatchResult>(`${this.baseUrl}/${id}/match`, {});
  }
}
