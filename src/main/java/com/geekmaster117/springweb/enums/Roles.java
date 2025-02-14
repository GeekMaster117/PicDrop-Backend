package com.geekmaster117.springweb.enums;

import java.util.List;

public enum Roles
{
    USER;

    public static List<Roles> getPrivileges(Roles role)
    {
        if (role.equals(Roles.USER))
        {
            return List.of(Roles.USER);
        }
        return List.of();
    }
}
