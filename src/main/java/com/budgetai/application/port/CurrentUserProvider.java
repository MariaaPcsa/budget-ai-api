package com.budgetai.application.port;

import java.util.UUID;

public interface CurrentUserProvider {

    UUID currentUserId();
}