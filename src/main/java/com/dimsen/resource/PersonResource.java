package com.dimsen.resource;

import com.dimsen.entity.Person;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Path(value = "/persons")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class PersonResource {

    @GET
    @Path(value = "/all")
    public Response getAll() {
        try {
            List<Person> personList = Person.listAll();
            if (personList.isEmpty()) {
                return Response.ok(Collections.EMPTY_LIST).build();
            }

            return Response.ok(personList).build();
        } catch (Exception e) {
            log.error("An error occurred in getting all persons. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path(value = "/get/{id}")
    public Response getPersonById(@PathParam("id") String id) {
        try {
            log.info("Get person by id: {}", id);
            ObjectId objectId = new ObjectId(id);

            Optional<Person> optionalPerson = Person.findByIdOptional(objectId);

            if (optionalPerson.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            Person person = optionalPerson.get();
            return Response.ok(person).build();

        } catch (IllegalArgumentException e) {
            log.error("Invalid ObjectId format: {}", id);
            return Response.status(Response.Status.BAD_REQUEST).build();

        } catch (Exception e) {
            log.error("An error occurred in getting person by id. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    public Response createPerson(Person person) {
        try {
            person.persist();
            return Response.ok(person).build();

        } catch (Exception e) {
            log.error("An error occurred in creating person. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path(value = "update/{id}")
    public Response updatePerson(@PathParam(value = "id") String id, Person person) {
        try {
            log.info("Update person by id: {}", id);
            ObjectId objectId = new ObjectId(id);

            Optional<Person> optionalPerson = Person.findByIdOptional(objectId);
            if (optionalPerson.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            Person personToUpdate = optionalPerson.get();
            personToUpdate.setName(person.getName());
            personToUpdate.setSurname(person.getSurname());
            personToUpdate.setEmail(person.getEmail());
            personToUpdate.setPhone(person.getPhone());
            personToUpdate.setBirthday(person.getBirthday());
            personToUpdate.persist();
            return Response.noContent().build();

        } catch (Exception e) {
            log.error("An error occurred in updating person by id. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path(value = "/delete/{id}")
    public Response deletePerson(@PathParam(value = "id") String id) {
        try {
            log.info("Delete person by id: {}", id);
            ObjectId objectId = new ObjectId(id);

            Optional<Person> person = Person.findByIdOptional(objectId);
            if (person.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            person.get().delete();
            return Response.ok().build();

        } catch (Exception e) {
            log.error("An error occurred in deleting person by id. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path(value = "/search/{name}")
    public Response searchPerson(@PathParam(value = "name") String name) {
        try {
            log.info("Searching for {}", name);
            Optional<Person> optionalPerson = Person.findByName(name);
            log.info("Person found: {}", optionalPerson.isPresent());
            if (optionalPerson.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(Person.findByName(name)).build();
        } catch (Exception e) {
            log.error("An error occurred in searching person by name. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path(value = "/count/alive")
    public Response countAlive() {
        try {
            return Response.ok((long) Person.findAlive().size()).build();
        } catch (Exception e) {
            log.error("An error occurred in counting alive. Error {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
