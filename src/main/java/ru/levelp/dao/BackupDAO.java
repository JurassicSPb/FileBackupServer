package ru.levelp.dao;

/**
 * Created by Мария on 07.10.2016.
 */
import ru.levelp.entities.BackupInfo;
import java.util.List;

public interface BackupDAO {

    void add(BackupInfo backupInfo);

    BackupInfo get(int id);

    List<BackupInfo> getAll();
}
