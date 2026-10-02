package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutReservation statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicule", nullable = false)
    private Vehicule vehicule;

    // Owning side: the foreign key id_contrat lives in the reservation table.
    // Nullable because a reservation exists before its contract is signed.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contrat", unique = true)
    private Contrat contrat;
}
