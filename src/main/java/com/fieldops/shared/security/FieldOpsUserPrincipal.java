package com.fieldops.shared.security;

import java.util.Set;
import java.util.UUID;

public record FieldOpsUserPrincipal(
    UUID userId, Set<Role> roles, Set<UUID> supervisorScopeSiteIds) {}
