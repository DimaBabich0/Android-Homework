package com.dima.hw03;

public class Flag {
    private String flagName;
    private String flagUrl;

    public Flag(String flagName, String flagUrl)
    {
        this.flagName = flagName;
        this.flagUrl = flagUrl;
    }

    public void setFlagName(String flagName) { this.flagName = flagName; }
    public String getFlagName() { return flagName; }

    public void setFlagUrl(String flagUrl) { this.flagUrl = flagUrl; }
    public String getFlagUrl() { return flagUrl; }
}
