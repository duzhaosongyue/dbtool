package com.csf.dbtool.util;

import com.csf.dbtool.model.DatabaseConnection;

import javax.servlet.http.HttpSession;

public class HttpSessionUtil {

    private static final String CONNECTION_KEY = "CONNECTION_KEY";


    public static void writSessionConnection(DatabaseConnection connection, HttpSession session) {
        DatabaseConnection connectionSession = getSession(session);
        if (connectionSession != null) {
            session.removeAttribute(CONNECTION_KEY);
        }
        session.setAttribute(CONNECTION_KEY, connection);
    }


    public static DatabaseConnection getSession(HttpSession session){
        return (DatabaseConnection) session.getAttribute(CONNECTION_KEY);
    }
}
