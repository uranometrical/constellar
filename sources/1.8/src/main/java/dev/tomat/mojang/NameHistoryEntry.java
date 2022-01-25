package dev.tomat.mojang;

public class NameHistoryEntry
{
    private String name;
    private long changedToAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getChangedToAt() {
        return changedToAt;
    }

    public void setChangedToAt(long changedToAt) {
        this.changedToAt = changedToAt;
    }

    @Override
    public String toString() {
        return "NameHistoryEntry [changedToAt=" + changedToAt + ", name=" + name + "]";
    }
}
