package ru.levelp.api;

/**
 * Created by Мария on 07.10.2016.
 */
//JsonRPC
public class RequestContainer {
    private int id;
    private String method;
    private long ts;
    private int backupId;
    private String backupName;
    private byte[] bytes;

    public int getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public long getTs() {
        return ts;
    }

    public int getBackupId() {
        return backupId;
    }

    public String getBackupName() {
        return backupName;
    }

    public byte[] getBytes() {
        return bytes;
    }
}