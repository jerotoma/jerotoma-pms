import { Person } from '../person.model';

export interface Parent extends Person {
    title?: string;
}
