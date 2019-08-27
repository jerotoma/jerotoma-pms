import { Position } from 'app/models';
import { Person } from '../person.model';

export interface Staff extends Person {
    title?: string;
    id?: number;
    position?: Position;
}
