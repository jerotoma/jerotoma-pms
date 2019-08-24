import { Person } from '../person.model';

export interface Staff extends Person {
    title?: string;
}
