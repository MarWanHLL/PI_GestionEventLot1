import { ProductCategory } from './association.model';

export enum DonationStatus {
  CREATED = 'CREATED',
  PENDING_PICKUP = 'PENDING_PICKUP',
  IN_TRANSIT = 'IN_TRANSIT',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED',
  MATCHED = 'MATCHED', // add any other statuses you have on the backend
}

export interface Donation {
  id: number;
  reference: string;

  merchantId: number;
  merchantName: string;
  merchantAddress: string;

  associationId?: number | null;
  associationName?: string | null;
  associationAddress?: string | null;

  status: DonationStatus;

  createdAt: string;
  pickupPlannedAt?: string | null;
  deliveredAt?: string | null;

  totalQuantity: number;
  quantityUnit: string;
  estimatedValue: number;

  currentLocation: string;

  category: ProductCategory;
}

export interface DonationCreatePayload {
  merchantId: number;
  merchantName: string;
  merchantAddress: string;
  totalQuantity: number;
  quantityUnit: string;
  estimatedValue: number;
  pickupPlannedAt: string | null;
  category: ProductCategory;
}

/** Shape of MatchResultResponse from backend (fields superset is OK) */
export interface MatchResult {
  donationId: number;
  associationId: number | null;
  associationName: string | null;
  score?: number | null;
  message?: string | null;
}
