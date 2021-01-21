
export interface ScoreStanding {
  id: number;
  minScore: number;
  maxScore: number;
  standing: string;
  standingColor: string;
  createdOn?: Date;
  updatedOn?: Date;
}
