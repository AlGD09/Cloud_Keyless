import { Smartphone } from './smartphone';

export interface Rcu {
  id?: number;
  rcuId: string;
  name: string;
  location: string;
  registeredAt?: string;
  assignedSmartphone?: Smartphone | null;
}
