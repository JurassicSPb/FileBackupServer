package ru.levelp.api;

/**
 * Created by Мария on 07.10.2016.
 */
import ru.levelp.entities.BackupInfo;
import ru.levelp.utils.DateUtil;

import java.util.ArrayList;

public class ResponseContainer {
    //Required fields
    private int id;
    private String method;
    private long ts;
    //Optional fields
    private Integer backupId;
    private ArrayList<BackupInfo> history;

    public ResponseContainer(int id, String method) {
        this.id = id;
        this.method = method;
        ts = DateUtil.now();
    }

    public void setBackupId(Integer backupId) {
        this.backupId = backupId;
    }

    public void setHistory(ArrayList<BackupInfo> history) {
        this.history = history;
    }
}