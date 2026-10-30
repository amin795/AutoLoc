package tn.esprit.autoloc.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

@ExtendWith(MockitoExtension.class)
class IAgenceRepositoryMockTest {

    @Mock
    private IAgenceRepository iAgenceRepository;

    @Test
    void saveAgence_shouldReturnAgenceWithId_whenRepositoryIsMocked() {
        Agence agence = buildAgence();

        when(iAgenceRepository.save(any(Agence.class))).thenAnswer(invocation -> {
            Agence savedAgence = invocation.getArgument(0);
            savedAgence.setIdAgence(1L);
            return savedAgence;
        });

        Agence saved = iAgenceRepository.save(agence);

        assertNotNull(saved.getIdAgence());
        assertEquals(2, saved.getVehicules().size());

        ArgumentCaptor<Agence> captor = ArgumentCaptor.forClass(Agence.class);
        verify(iAgenceRepository).save(captor.capture());
        assertEquals("Agence ariana", captor.getValue().getNom());
    }

    @Test
    void loadAgence_shouldWorkWithMockedRepositoryData() {
        Agence agence = buildAgence();
        agence.setIdAgence(13L);
        agence.getVehicules().get(0).setIdVehicule(23L);
        agence.getVehicules().get(1).setIdVehicule(24L);

        when(iAgenceRepository.findAll()).thenReturn(List.of(agence));

        StringBuilder sb = new StringBuilder();
        iAgenceRepository.findAll().forEach(item -> {
            sb.append("IAgenceRepository").append('\n');
            sb.append(item.getIdAgence()).append(" | ").append(item.getNom()).append('\n');
            sb.append("Vehicules Count : ").append(item.getVehicules().size()).append('\n');
            item.getVehicules().forEach(vehicule -> sb.append("=== ")
                    .append(vehicule.getIdVehicule())
                    .append('|')
                    .append(vehicule.getImmatriculation())
                    .append('\n'));
        });

        String output = sb.toString();
        assertTrue(output.contains("IAgenceRepository"));
        assertTrue(output.contains("13 | Agence ariana"));
        assertTrue(output.contains("Vehicules Count : 2"));
        assertTrue(output.contains("=== 23|123 TUN 558287034"));
        assertTrue(output.contains("=== 24|234 TUN 558287035"));
    }

    private Agence buildAgence() {
        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setVille("Tunis");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");

        Vehicule v1 = new Vehicule();
        v1.setImmatriculation("123 TUN 558287034");
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setCategorie(CategorieVehicule.SUV);
        v1.setStatut(StatutVehicule.MAINTENANCE);
        v1.setTarifJournalier(new BigDecimal("100"));

        Vehicule v2 = new Vehicule();
        v2.setImmatriculation("234 TUN 558287035");
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

