package com.dimsen.resource;

import com.dimsen.dto.FruitDto;
import com.dimsen.entity.Fruit;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Path(value = "/fruits")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class FruitResource {

    @POST
    public Response addFruit(FruitDto fruitDto) {
        try {
            log.info("Add fruit {}", fruitDto);
            Fruit fruit = new Fruit();
            fruit.setName(fruitDto.getName());
            fruit.setColor(fruitDto.getColor());
            fruit.persist();

            FruitDto dto = new FruitDto();
            dto.setName(fruitDto.getName());
            dto.setColor(fruitDto.getColor());

            return Response.ok(fruitDto).build();
        } catch (Exception e) {
            log.error("An error occurred in adding fruit. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path(value = "/get/{id}")
    public Response getFruitById(@PathParam("id") String id) {
        try {
            log.info("Get fruit by id {}", id);
            ObjectId objectId = new ObjectId(id);

            Optional<Fruit> fruit = Fruit.findByIdOptional(objectId);
            if (fruit.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            FruitDto dto = new FruitDto();
            dto.setName(fruit.get().getName());
            dto.setColor(fruit.get().getColor());
            return Response.ok(dto).build();
        } catch (Exception e) {
            log.error("An error occurred in getFruitById. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path(value = "/all")
    public Response getAllFruits() {
        try {
            log.info("Get all fruits");
            List<Fruit> fruits = Fruit.listAll();
            List<FruitDto> dtos = new ArrayList<>();

            for (Fruit fruit : fruits) {
                FruitDto dto = new FruitDto();
                dto.setName(fruit.getName());
                dto.setColor(fruit.getColor());
                dtos.add(dto);
            }

            return Response.ok(dtos).build();
        } catch (Exception e) {
            log.error("An error occurred in getAllFruits. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path(value = "/update/{id}")
    public Response updateFruit(@PathParam("id") String id, FruitDto fruitDto) {
        try {
            log.info("Update fruit {}", fruitDto);
            ObjectId objectId = new ObjectId(id);
            Optional<Fruit> fruit = Fruit.findByIdOptional(objectId);
            if (fruit.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            Fruit fruit1 = fruit.get();
            fruit1.setName(fruitDto.getName());
            fruit1.setColor(fruitDto.getColor());
            fruit1.persist();

            FruitDto dto = new FruitDto();
            dto.setName(fruitDto.getName());
            dto.setColor(fruitDto.getColor());
            return Response.ok(dto).build();
        } catch (Exception e) {
            log.error("An error occurred in updateFruit. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path(value = "/delete/{id}")
    public Response deleteFruit(@PathParam("id") String id) {
        try {
            log.info("Delete fruit {}", id);
            ObjectId objectId = new ObjectId(id);
            Optional<Fruit> fruit = Fruit.findByIdOptional(objectId);
            if (fruit.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            fruit.get().delete();

            return Response.ok().build();
        } catch (Exception e) {
            log.error("An error occurred in deleteFruit. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path(value = "/search/{name}")
    public Response searchFruit(@PathParam("name") String name) {
        try {
            log.info("Search fruit {}", name);
            List<Fruit> fruits = Fruit.findByName(name);
            List<FruitDto> dtos = new ArrayList<>();
            for (Fruit fruit : fruits) {
                FruitDto dto = new FruitDto();
                dto.setName(fruit.getName());
                dto.setColor(fruit.getColor());
                dtos.add(dto);
            }
            return Response.ok(dtos).build();
        } catch (Exception e) {
            log.error("An error occurred in searchFruit. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
