export const environment = {
  production: true,
  apiUrl: window["env"]["apiUrl"] || "default",
  apiWebsocket: window["env"]["apiWebsocket"] || "default",
  debug: window["env"]["debug"] || false
};
