package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "equipment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipment;

    @Column(nullable = false, length = 100)
    private String libelle;

    // Inverse side of the ManyToMany: loading and deletion are not linked -> no cascade
    @ManyToMany(mappedBy = "equipments", fetch = FetchType.LAZY)
    private List<Vehicule> vehicules = new ArrayList<>();
}