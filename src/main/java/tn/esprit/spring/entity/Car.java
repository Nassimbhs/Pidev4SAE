package tn.esprit.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String carModel;
    private String carStatus;
    private String matricule;
    private String image;
    private float price;
    private float rating;
    private int seats;
    private String gearbox;
    @Temporal(TemporalType.TIMESTAMP)
    private Date StartDateLocation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date EndDateLocation;
    private float day;

    @JsonIgnore
    @ManyToOne
    Employee employees;

    @JsonIgnore
    @ManyToOne
    Company companys;

}
