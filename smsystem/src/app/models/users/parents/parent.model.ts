import { User } from '../user.model';

export interface Parent extends User {
    id: number;
    title?: string;
    studentIds: number[];
}
