package Enums;


public enum SupportType {

    COMPLAINT("Complaint"), QUESTION("question");
    private final String kind;

    SupportType(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }
}