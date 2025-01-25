package com.devapi.api.domain.model;

import com.devapi.api.utils.EditionStatus;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Edition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;
    private int year;
    private Date initialDate;
    private Date finalDate;
    private String city;
    @Nullable
    private EditionStatus status;

    @OneToOne
    @JoinColumn(name = "fk_evento", nullable=false)
    private Event event;
}
