package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "contrat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;

    @Column(nullable = false)
    private LocalDate dateSignature;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @Column(nullable = false)
    private Boolean valide;

    // Inverse side: the foreign key is in the reservation table (field "contrat")
    @OneToOne(mappedBy = "contrat", fetch = FetchType.LAZY)
    private Reservation reservation;

    // Loading a contract loads its payments
    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Paiement> paiements = new ArrayList<>();
}
