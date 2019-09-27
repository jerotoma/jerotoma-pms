import { Person } from '../person.model';

export interface Parent extends Person {
    id: number;
    title?: string;
    studentIds: number[];
}
