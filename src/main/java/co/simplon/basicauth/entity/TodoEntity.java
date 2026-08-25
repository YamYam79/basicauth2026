package co.simplon.basicauth.entity;

import org.hibernate.Hibernate;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "todo")


public class TodoEntity {
    
//CTodoEntity représente simplement les données que l'utilisateur manipule après s'être authentifié
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Nonnull
    @Column(nullable = false)
    public String title;

    @Nonnull
    //columnDefinition, permet de demander à Hibernate de créer une colonne SQL ressemblant à done BOOLEAN DEFAULT false
    @Column(columnDefinition = "BOOLEAN DEFAULT false", nullable = false)
    public Boolean done = false;

    //Constructeur vide TodoEntity => JPA/Hibernate en a besoin pour créer les objets lorsqu'il lit les données depuis la base.
    public TodoEntity() {
    }

//Constructeur avec titre => Permet de créer rapidement une tâche
    public TodoEntity(String title) {
        this.title = title;
    }
//Constructeur complet => Si done vaut null, alors mettre false, sinon utiliser la valeur fournie.
    public TodoEntity(String title, Boolean done) {
        this.title = title;
        this.done = done == null ? false : done;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDone() {
        return this.done;
    }

    public void setDone(Boolean done) {
        this.done = done == null ? false : done;
    }

    @Override
    public String toString() {
        return String.format("Todo: { id='%s', title='%s', done='%s' }", getId(), getTitle(), getDone());
    }

}