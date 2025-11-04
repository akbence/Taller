package hu.codemosaic.taller.controller;

import hu.codemosaic.taller.security.UserContext;

import java.util.UUID;

public abstract class BaseApiController {
    protected static final String BASE_PATH = "/api/v1";

    protected String getCurrentUsername() {
        return UserContext.getUsername();
    }

    protected UUID getCurrentUserId() {
        return UserContext.getUserId();
    }
}