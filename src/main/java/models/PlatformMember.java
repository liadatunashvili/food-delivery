package models;

public abstract class PlatformMember implements RoleDescribable {

    protected static final String DEFAULT_PLATFORM_TYPE;

    static {
        DEFAULT_PLATFORM_TYPE = "Food Delivery";
    }

    protected String platformType;
    protected boolean active;

    protected PlatformMember(String platformType) {
        this.platformType = platformType != null ? platformType : DEFAULT_PLATFORM_TYPE;
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

    public final String describeMemberState() {
        return getRoleName() + " ON " + platformType + "IS ACTIVE: " + active;
    }

    public abstract String getRoleName();
}
