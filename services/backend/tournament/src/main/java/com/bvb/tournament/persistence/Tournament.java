package com.bvb.tournament.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tournament")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tournament_name")
    private String tournamentName;

    @Column(name = "logoId")
    private String logoId;

    @Column(name = "category")
    private CategoryEnum category;
}
