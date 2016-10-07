package ru.levelp.entities;

/**
 * Created by Мария on 07.10.2016.
 */
import javax.persistence.*;

@Entity
@Table(name = "backups")
public class BackupInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private String created;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreated() {
        return created;
    }
}
