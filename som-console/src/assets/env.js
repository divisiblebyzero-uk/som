// Inspiration from: https://pumpingco.de/blog/environment-variables-angular-docker/#

(function(window) {
    window["env"] = window["env"] || {};
  
    // Environment variables
    window["env"]["apiUrl"] = "http://localhost:8080";
    window["env"]["apiWebsocket"] = "ws://localhost:8080";
    window["env"]["debug"] = true;
  })(this);