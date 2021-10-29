package fr.alainncls.demospringrestdocs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    private String id;
    private String name;
    private String location;
    private Date creationDate;
}
