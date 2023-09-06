package com.lembrete.lembrete.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lembretes", schema = "public")
public class Lembretes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter @Setter
    private String recado;

    @Getter @Setter
    @JoinColumn(name = "pessoa_id", nullable = false)
    @ManyToOne
    private Pessoas pessoa;


}
