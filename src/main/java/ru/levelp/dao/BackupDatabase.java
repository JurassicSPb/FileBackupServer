package ru.levelp.dao;

/**
 * Created by Мария on 07.10.2016.
 */
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.levelp.entities.BackupInfo;

import java.util.List;


public class BackupDatabase implements BackupDAO{
    private static final BackupDatabase instance = new BackupDatabase();

    private BackupDatabase() {

    }

    public static BackupDatabase getInstance() {
        return instance;
    }


    public void add(BackupInfo backupInfo) {
        Session session = HibernateManager.getInstance()
                .getSessionFactory()
                .openSession();
        session.beginTransaction();
        session.save(backupInfo);
        session.getTransaction().commit();
        session.close();
    }

    public BackupInfo get(int id) {
        Session session = HibernateManager.getInstance()
                .getSessionFactory()
                .openSession();
        BackupInfo backupInfo = (BackupInfo) session.createCriteria(BackupInfo.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return backupInfo;
    }

    public List<BackupInfo> getAll() {
        Session session = HibernateManager.getInstance()
                .getSessionFactory()
                .openSession();
        List<BackupInfo> backups = session.createCriteria(BackupInfo.class).list();
        session.close();
        return backups;
    }
//    public List<BackupInfo> getHistory(String created) {
//        Session session = HibernateManager.getInstance()
//                .getSessionFactory()
//                .openSession();
//        List<BackupInfo> backups = ;
//        //с коллекцией никаких преобразований не делается
//        return backups;
//    }

}
