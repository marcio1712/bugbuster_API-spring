package com.devapi.api.domain.model;

import com.devapi.api.utils.TicketStatus;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "fk_user", nullable=false)
    private User user;

    @Nullable
    private TicketStatus status;

    @OneToOne
    @JoinColumn(name = "fk_edition", nullable=false)
    private Edition edition;
}
