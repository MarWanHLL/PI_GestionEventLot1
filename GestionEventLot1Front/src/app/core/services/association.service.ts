// src/app/core/services/association.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environments';

import {
  Association,
  AssociationCreatePayload,
  AssociationNeed,
  AssociationNeedCreatePayload,
} from '../models/association.model';

@Injectable({
  providedIn: 'root',
})
export class AssociationService {
  private readonly baseUrl = `${environment.apiUrl}/associations`;

  constructor(private http: HttpClient) {}

  // ==== Associations CRUD ====

  getAll(): Observable<Association[]> {
    return this.http.get<Association[]>(this.baseUrl);
  }

  getById(id: number): Observable<Association> {
    return this.http.get<Association>(`${this.baseUrl}/${id}`);
  }

  create(payload: AssociationCreatePayload): Observable<Association> {
    return this.http.post<Association>(this.baseUrl, payload);
  }

  update(
    id: number,
    payload: AssociationCreatePayload
  ): Observable<Association> {
    return this.http.put<Association>(`${this.baseUrl}/${id}`, payload);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // ==== Needs ====

  getNeeds(associationId: number): Observable<AssociationNeed[]> {
    return this.http.get<AssociationNeed[]>(
      `${this.baseUrl}/${associationId}/needs`
    );
  }

  addNeed(
    associationId: number,
    payload: AssociationNeedCreatePayload
  ): Observable<AssociationNeed> {
    return this.http.post<AssociationNeed>(
      `${this.baseUrl}/${associationId}/needs`,
      payload
    );
  }
}
