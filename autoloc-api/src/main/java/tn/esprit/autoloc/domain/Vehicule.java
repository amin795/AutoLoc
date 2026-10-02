package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.*;
import java.util.*;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    @Column(nullable = false, unique = true, length = 20)
    private String immatriculation;

    @Column(nullable = false, length = 50)
    private String marque;

    @Column(nullable = false, length = 50)
    private String modele;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategorieVehicule categorie;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutVehicule statut;

    // A vehicle can exist without an agency -> nullable (default)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agence")
    private Agence agence;

    // Loading and deletion are not linked -> no cascade
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vehicule_equipment",
            joinColumns = @JoinColumn(name = "id_vehicule"),
            inverseJoinColumns = @JoinColumn(name = "id_equipment")
    )
    private List<Equipment> equipments = new ArrayList<>();

    // Deleting a vehicle deletes its reservations; loading does not load them
    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY)
    private List<Maintenance> maintenances = new ArrayList<>();
}