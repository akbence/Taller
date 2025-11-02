package hu.codemosaic.taller.controller;

import hu.codemosaic.taller.security.UserContext;

public abstract class BaseApiController {
    protected static final String BASE_PATH = "/api/v1";

    protected String getCurrentUsername() {
        return UserContext.getUsername();
    }
}