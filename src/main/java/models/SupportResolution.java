package models;

public class SupportResolution {

    private final Support support;
    private final RoleDescribable resolvedBy;
    private final String message;

    public SupportResolution(Support support, RoleDescribable resolvedBy, String message) {
        this.support = support;
        this.resolvedBy = resolvedBy;
        this.message = message;
    }

    public Support getSupport() {
        return support;
    }

    public RoleDescribable getResolvedBy() {
        return resolvedBy;
    }

    public String getResolvedByLabel() {
        if (resolvedBy instanceof User user) {
            return user.getName();
        }
        return resolvedBy != null ? resolvedBy.getRoleName() : "Unknown resolver";
    }

    public String getMessage() {
        return message;
    }
}
