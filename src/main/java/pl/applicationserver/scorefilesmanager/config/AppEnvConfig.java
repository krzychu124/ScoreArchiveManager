package pl.applicationserver.scorefilesmanager.config;

import org.springframework.stereotype.Component;

@Component
public class AppEnvConfig {
    private String DropboxToken = "";

    AppEnvConfig() {
        getStorageToken();
    }

    public String getDropboxToken() {
        return DropboxToken;
    }

    private void getStorageToken() {
        this.DropboxToken = System.getenv("DROPBOX_AUTHORIZATION_TOKEN");
    }

}
