// src/app/core/models/association.model.ts

export interface Association {
  id: number;
  name: string;
  address?: string;
  city?: string;
  zone?: string;
  location?: string | null; // lat,lng or any geo string
  active: boolean;
}

export interface AssociationCreatePayload {
  name: string;
  address?: string;
  city?: string;
  zone?: string;
  location?: string | null;
}

export enum ProductCategory {
  FRUITS_VEGETABLES = 'FRUITS_VEGETABLES',
  DAIRY = 'DAIRY',
  MEAT_FISH = 'MEAT_FISH',
  BAKERY = 'BAKERY',
  DRY_GOODS = 'DRY_GOODS',
  DRINKS = 'DRINKS',
  OTHER = 'OTHER',
}

/**
 * Need as returned by backend (/api/associations/{id}/needs)
 */
export interface AssociationNeed {
  id: number;
  category: ProductCategory;
  minQuantity?: number | null;
  maxQuantity?: number | null;
  associationId?: number; // useful for stats, optional
}

/**
 * Payload for creating a new need
 */
export interface AssociationNeedCreatePayload {
  category: ProductCategory;
  minQuantity?: number | null;
  maxQuantity?: number | null;
}
