package sercurity;

import java.io.Serializable;
import java.security.Principal;

public class PrincipalImpl implements Principal, Serializable {
    private final String name;

    public PrincipalImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof PrincipalImpl that))
            return false;

        return this.getName().equals(that.getName());
    }
}
