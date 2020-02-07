
export class ResponseWrapper {
  constructor(
    public data?: any,
    public success?: boolean,
    public message?: string,
    public statusCode?: string,
    public httpStatus?: any,
  ) {}
}
