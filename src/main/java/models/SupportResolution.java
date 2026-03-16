package models;

public class SupportResolution {

    private final Support support;
    private final String resolvedBy;
    private final String message;

    public SupportResolution(Support support, String resolvedBy, String message) {
        this.support = support;
        this.resolvedBy = resolvedBy;
        this.message = message;
    }

    public Support getSupport() {
        return support;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public String getMessage() {
        return message;
    }
}

