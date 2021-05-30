(function(window) {
    window.env = window.env || {};
  
    // Environment variables
    window["env"]["apiUrl"] = "${API_URL}";
    window["env"]["apiWebsocket"] = "${API_WEBSOCKET}";
    window["env"]["debug"] = "${DEBUG}";
  })(this);