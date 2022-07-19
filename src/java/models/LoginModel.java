package models;

import java.io.Serial;
import java.io.Serializable;

public class LoginModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 10125479657L;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
