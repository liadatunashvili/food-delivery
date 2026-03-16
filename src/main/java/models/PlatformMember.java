package models;

public abstract class PlatformMember {

    protected String platformType;
    protected boolean active;

    protected PlatformMember(String platformType) {
        this.platformType = platformType;
        this.active = true;
    }

    public String getPlatformType() {
        return platformType;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    public abstract String getRoleName();
}
