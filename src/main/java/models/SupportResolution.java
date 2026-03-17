package models;

public class SupportResolution {

    private final Support support;
    private final PlatformMember resolvedBy;
    private final String message;

    public SupportResolution(Support support, PlatformMember resolvedBy, String message) {
        this.support = support;
        this.resolvedBy = resolvedBy;
        this.message = message;
    }

    public Support getSupport() {
        return support;
    }

    public PlatformMember getResolvedBy() {
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

