export const HTTP_STATUS_CODES = {

  // Success Responses
  CODE_200: {
    id: 200,
    message: 'OK',
  },
  CODE_201: {
    id: 201,
    message: 'Created',
  },
  CODE_202: {
    id: 202,
    message: 'Accepted',
  },

  // Redirection Responses
  CODE_300: {
    id: 300,
    message: 'Multiple Choices',
  },
  CODE_301: {
    id: 301,
    message: 'Moved Permanently',
  },
  CODE_302: {
    id: 302,
    message: 'Found',
  },
  CODE_303: {
    id: 303,
    message: 'See Other',
  },

// Client Error Responses
  CODE_400: {
    id: 400,
    message: 'Bad Request',
  },
  CODE_401: {
    id: 401,
    message: 'Unauthorized',
  },
  CODE_402: {
    id: 402,
    message: 'Payment Required',
  },
  CODE_403: {
    id: 403,
    message: 'Forbidden',
  },
  CODE_404: {
    id: 404,
    message: 'Not Found',
  },
  CODE_405: {
    id: 405,
    message: 'Method not found',
  },
};
