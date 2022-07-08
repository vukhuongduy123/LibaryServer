package models;

import java.io.Serializable;
import java.util.Arrays;

public class MessageModel implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String message;
    private Object[] args = null;

    public MessageModel() {}

    public MessageModel(String message, Object[] args) {
        this.message = message;
        this.args = args;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "message='" + message + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
