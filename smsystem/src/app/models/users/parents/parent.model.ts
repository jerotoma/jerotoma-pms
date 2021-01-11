import { User } from '../user.model';

export interface Parent extends User {
    id: number;
    title?: string;
    studentIds?: number[];
    studentId?: number;
    relationshipType?: string;
}

export interface ParentWrapper {
  valid: boolean;
  parent: Parent;
}
