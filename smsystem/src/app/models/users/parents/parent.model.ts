import { Person } from '../person.model';

export interface Parent extends Person {
    id: Number;
    title?: string;
}
