package com.dimsen.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MongoEntity(collection = "PERSONS")
public class Person extends PanacheMongoEntity {

    public String name;
    public String surname;
    public String email;
    public String phone;
    @BsonProperty(value = "birth")
    public LocalDate birthday;
    public Status status;

    public String getName() {
        return name.toUpperCase();
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public String getSurname() {
        return surname.toUpperCase();
    }

    public void setSurname(String surname) {
        this.surname = surname.toLowerCase();
    }

    public static Optional<Person> findByName(String name) {
        return find("name", name).firstResultOptional();
    }

    public static List<Person> findAlive() {
        return list("status", Status.ALIVE);
    }
}
