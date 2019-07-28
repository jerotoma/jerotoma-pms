

export interface Token {
  exp: Date;
  iat: Date;
  iss: string;
  scopes: string[];
  sub: string;
}
