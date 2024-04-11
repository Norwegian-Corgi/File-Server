import Keycloak from "keycloak-js";

export const useKeycloak = () => {
  const keycloak = useState<Keycloak>("keycloak");

  const init = () => {
    const runtimeConfig = useRuntimeConfig();
    const url = "http://" + runtimeConfig.public.serverIp + ":8080";
    keycloak.value = new Keycloak({
      url: url,
      realm: "file-server",
      clientId: "backend",
    });
  };

  const login = async () => {
    await keycloak.value.init({
      onLoad: "login-required",
    });
  };

  const updateToken = async () => {
    await keycloak.value.updateToken();
  };

  const getAccessToken = async () => {
    if (!keycloak.value) {
      init();
    }
    if (!keycloak.value.authenticated) {
      await login();
    }
    if (keycloak.value.isTokenExpired()) {
      await updateToken();
    }
    return keycloak.value.token;
  };

  const logout = async () => await keycloak.value.logout();

  return {
    getAccessToken,
    logout,
  };
};
