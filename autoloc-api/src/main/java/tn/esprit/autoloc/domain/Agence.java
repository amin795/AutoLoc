package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String ville;

    @Column(nullable = false, length = 150)
    private String adresse;

    @Column(nullable = false, length = 20)
    private String telephone;

    // Saving an agency saves its vehicles; loading an agency loads its vehicles
    @OneToMany(mappedBy = "agence", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Vehicule> vehicules = new ArrayList<>();

    // Loading an agency does not load employees; deleting it does not delete them
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Employee> employes = new ArrayList<>();
}