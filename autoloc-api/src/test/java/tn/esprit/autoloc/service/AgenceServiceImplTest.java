package tn.esprit.autoloc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

@SpringBootTest
class AgenceServiceImplTest {

    @Autowired
    private AgenceServiceImpl agenceService;

    @Test
    void createAgence_shouldCreateAgenceSuccessfully() {
        Agence agence = buildAgence();

        Agence saved = agenceService.create(agence);

        assertNotNull(saved.getIdAgence());
        assertEquals("Agence ariana", saved.getNom());
    }

    @Test
    void createAgence_shouldThrowIllegalArgumentException_whenAgenceIsNull() {
        assertThrows(IllegalArgumentException.class, () -> agenceService.create(null));
    }

    @Test
    void findById_shouldThrowException_whenAgenceDoesNotExist() {
        assertThrows(RuntimeException.class, () -> agenceService.findById(999999L));
    }

    private Agence buildAgence() {
        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setVille("Tunis");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");

        Vehicule v1 = new Vehicule();
        v1.setImmatriculation("123 TUN 9001");
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setCategorie(CategorieVehicule.SUV);
        v1.setStatut(StatutVehicule.MAINTENANCE);
        v1.setTarifJournalier(new BigDecimal("100"));

        Vehicule v2 = new Vehicule();
        v2.setImmatriculation("234 TUN 9002");
        v2.setMarque("Isuzu");
        v2.setModele("DMax");
        v2.setCategorie(CategorieVehicule.SUV);
        v2.setStatut(StatutVehicule.MAINTENANCE);
        v2.setTarifJournalier(new BigDecimal("100"));

        v1.setAgence(agence);
        v2.setAgence(agence);
        agence.getVehicules().add(v1);
        agence.getVehicules().add(v2);

        return agence;
    }
}

