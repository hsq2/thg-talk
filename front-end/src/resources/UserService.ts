import Keycloak from "keycloak-js";

const kc: Keycloak.KeycloakInstance = Keycloak("/keycloak.json");

const initKeyCloak = (onAuthCallback: () => void) => {
  kc.init({
    onLoad: "login-required",
    checkLoginIframe: false,
  }).success((authenticated) => {
    onAuthCallback();
  });
};

const login = kc.login;

const logout = kc.logout;

const authenticated = () => kc.authenticated;

const userID = () => kc.tokenParsed?.sub;

const userProfile = kc.loadUserProfile;

const getToken = () => kc.token;

const updateToken = kc.updateToken;

const UserService = {
  initKeyCloak,
  authenticated,
  login,
  logout,
  userID,
  userProfile,
  getToken,
  updateToken,
};

export default UserService;
