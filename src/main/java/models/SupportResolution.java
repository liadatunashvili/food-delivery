package models;

public class SupportResolution {
    private final String resolvedBy;
    private final String message;

    public SupportResolution(String resolvedBy, String message) {
        this.resolvedBy = resolvedBy;
        this.message = message;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public String getMessage() {
        return message;
    }
}

