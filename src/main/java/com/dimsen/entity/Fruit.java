package com.dimsen.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "FRUITS")
public class Fruit extends PanacheMongoEntity {

    @BsonProperty(value = "fruit_name")
    public String name;
    public String color;

    public String getName() {
        return name.toUpperCase();
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public static List<Fruit> findByName(String name) {
        return find("fruit_name", name).list();
    }
}
