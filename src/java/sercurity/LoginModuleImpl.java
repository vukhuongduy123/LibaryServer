package sercurity;

import connection.SQLConnection;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public class LoginModuleImpl implements LoginModule {
    private CallbackHandler callbackHandler = null;
    private Subject subject = null;
    private PrincipalImpl principal = null;
    private boolean succeed = false;
    private boolean commitFlag = true;

    private String username;
    private String password;


    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.callbackHandler = callbackHandler;
        this.subject = subject;
    }

    @Override
    public boolean login() throws LoginException {

        try {
            Callback[] callbacks = new Callback[2];
            callbacks[0] = new NameCallback("User name: ");
            callbacks[1] = new PasswordCallback("Password: ", false);
            callbackHandler.handle(callbacks);

            username = ((NameCallback) callbacks[0]).getName();
            password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());

            Connection connection = SQLConnection.getConnection();
            String sqlQuery = "select username, pass from Users where username = ? and pass = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                System.out.println("Authentication success");
                succeed = true;
            } else {
                System.out.println("Authentication fail");
                succeed = false;
            }

        } catch (SQLException | IOException | UnsupportedCallbackException throwables) {
            throwables.printStackTrace();
        }

        return succeed;
    }

    @Override
    public boolean commit() throws LoginException {
        if (!succeed)
            return false;
        principal = new PrincipalImpl(username);
        Set<Principal> principals = subject.getPrincipals();
        principals.add(principal);
        commitFlag = true;
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        if (!succeed)
            return false;
        if (commitFlag) {
            succeed = false;
            username = null;
            password = null;
            principal = null;
        } else {
            logout();
        }
        return true;

    }

    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(principal);
        succeed = false;
        commitFlag = false;
        username = null;
        password = null;
        principal = null;

        return true;
    }
}
