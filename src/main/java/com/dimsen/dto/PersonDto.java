package com.dimsen.dto;

import com.dimsen.entity.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.dimsen.entity.Person}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PersonDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -7495769902629679915L;
    String name;
    String surname;
    String email;
    String phone;
    LocalDate birthday;
    Status status;
}
