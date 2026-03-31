package models;

public record SupportResolution(Support support, RoleDescribable resolvedBy, String message) {

    public String getResolvedByLabel() {
        if (resolvedBy instanceof User user) {
            return user.getName();
        }
        return resolvedBy != null ? resolvedBy.getRoleName() : "Unknown resolver";
    }
}
