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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservation", nullable = false, unique = true)
    private Reservation reservation;

    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Paiement> paiements = new ArrayList<>();
}
