const STORE_NAME = 'authToken';
const USER = 'user';
const COMPANY = 'company';

let currentUser;

const getAuthenticatedUser = () => {
  if (currentUser !== undefined) return currentUser;

  const serializedUser = localStorage.getItem(USER);
  const serializedCompany = localStorage.getItem(COMPANY);

  console.log('Stored user:', serializedUser);
  console.log('Stored company:', serializedCompany);

  // Try to get company first, then fall back to user
  const storedData = serializedCompany || serializedUser;

  if (!storedData) return undefined;

  try {
    currentUser = JSON.parse(storedData);
    console.log('Parsed currentUser:', currentUser);
    return currentUser;
  } catch (error) {
    console.error('Error parsing stored user data:', error);
    return undefined;
  }
};

const setAuthenticatedUser = (authenticatedUser, token, role) => {
  const serializedUser = JSON.stringify(authenticatedUser);
  console.log('Setting authenticated user:', { authenticatedUser, role });

  localStorage.setItem(STORE_NAME, token);

  // Clear both storages first to avoid conflicts
  localStorage.removeItem(USER);
  localStorage.removeItem(COMPANY);

  if (role === 'company') {
    localStorage.setItem(COMPANY, serializedUser);
  } else if (role === 'admin') {
    localStorage.setItem(USER, serializedUser);
  }

  currentUser = authenticatedUser;
};

const clearAuthenticatedUser = () => {
  localStorage.removeItem(STORE_NAME);
  localStorage.removeItem(USER);
  localStorage.removeItem(COMPANY);
  sessionStorage.clear();
  currentUser = undefined;
};

const isAuthenticated = () => {
  const token = localStorage.getItem(STORE_NAME);
  const user = localStorage.getItem(USER);
  const company = localStorage.getItem(COMPANY);

  return !!(token && (user || company));
};

const getToken = () => {
  return localStorage.getItem(STORE_NAME);
};

const getUserType = () => {
  const user = localStorage.getItem(USER);
  const company = localStorage.getItem(COMPANY);

  if (company) {
    return 'company';
  } else if (user) {
    return 'user';
  }

  return null; // Aucun utilisateur connecté
};


export {
  setAuthenticatedUser,
  getAuthenticatedUser,
  isAuthenticated,
  clearAuthenticatedUser,
  getToken,
  getUserType
};
