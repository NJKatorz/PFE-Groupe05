const STORE_NAME = 'authToken';
const USER = 'user';
const COMPANY = 'company';

let currentUser;

/**
 * Retrieves the information of the currently authenticated user.
 * @returns The information of the currently authenticated user, or undefined if there's no authenticated user.
 */
const getAuthenticatedUser = () => {
  if (currentUser !== undefined) return currentUser;

  let serializedUser = localStorage.getItem(USER);
  if (serializedUser === null){
    serializedUser = localStorage.getItem(COMPANY);
    console.log("company dans getAuth : ", serializedUser);
  }

  if (!serializedUser) return undefined;
  currentUser = JSON.parse(serializedUser);
  return currentUser;
};

/**
 * Stores the information of the authenticated user.
 * @param  authenticatedUser the information of the authenticated user.
 */
const setAuthenticatedUser = (authenticatedUser, token, role) => {
  const serializedUser = JSON.stringify(authenticatedUser);
  console.log("serializedUser", serializedUser);
  localStorage.setItem(STORE_NAME, token);
  if (role === 'company')
  localStorage.setItem(COMPANY, serializedUser);
  else if (role === 'admin') localStorage.setItem(USER, serializedUser);
  currentUser = authenticatedUser;
  console.log("authenticatedUser : ", currentUser);
};


/**
 * Clears all authentication information of the user.
 */
const clearAuthenticatedUser = () => {
  localStorage.clear();
  sessionStorage.clear();
  currentUser = undefined;
};

/**
 * Checks if a user is currently authenticated.
 * @returns true if a user is authenticated, otherwise false.
 */
const isAuthenticated = () => currentUser !== undefined;


/**
 * Retrieves the authentication token of the currently authenticated user.
 * @returns The authentication token, or undefined if no user is authenticated.
 */
const getToken = ()=>{
  const token = localStorage.getItem(STORE_NAME);
  if (!token) return undefined;
  return token;
}

export {
  setAuthenticatedUser,
  getAuthenticatedUser,
  isAuthenticated,
  clearAuthenticatedUser,
  getToken,
};
