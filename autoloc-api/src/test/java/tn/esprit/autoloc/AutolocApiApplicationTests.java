package tn.esprit.autoloc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AutolocApiApplicationTests {

    @Autowired
    private CrudRepository<Agence, Long> basicAgenceRepository;

    @Autowired
    private IAgenceRepository fullAgenceRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    void cleanDatabase() {
        jdbcTemplate.execute("delete from vehicule");
        jdbcTemplate.execute("delete from agence");
    }

    @Test
    void basicAddAgence() {
        Agence agence = addAgence(basicAgenceRepository);
        assertNotNull(agence.getIdAgence());
    }

    @Test
    void fullAddAgence() {
        Agence agence = addAgence(fullAgenceRepository);
        assertNotNull(agence.getIdAgence());
    }

    @Test
    void basicLoadAgence() {
        loadAgence("CrudRepository", basicAgenceRepository);
    }

    @Test
    void fullLoadAgence() {
        loadAgence("IAgenceRepository", fullAgenceRepository);
    }

    @Test
    void loadSortedAgences() {
        createAgencesIfNeeded(5);

        StringBuilder sb = new StringBuilder();
        fullAgenceRepository.findAll(Sort.by(Sort.Direction.DESC, "idAgence")).forEach(agence -> {
            sb.append(agence.getIdAgence()).append(" | ").append(agence.getNom()).append('\n');
            sb.append(agence.getVille()).append(" | ").append(agence.getAdresse()).append('\n');
        });

        System.out.println(sb);
        assertTrue(sb.length() > 0);
    }

    @Test
    void loadPagedAgences() {
        createAgencesIfNeeded(5);

        StringBuilder sb = new StringBuilder();
        Page<Agence> agencePage = fullAgenceRepository.findAll(PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "idAgence")));
        sb.append("Total Pages : ").append(agencePage.getTotalPages()).append('\n');
        sb.append("Current Page : ").append(agencePage.getNumber()).append('\n');

        agencePage.getContent().forEach(agence -> {
            sb.append(agence.getIdAgence()).append(" | ").append(agence.getNom()).append('\n');
            sb.append(agence.getVille()).append(" | ").append(agence.getAdresse()).append('\n');
        });

        System.out.println(sb);
        assertTrue(sb.length() > 0);
    }

    private void createAgencesIfNeeded(int count) {
        if (fullAgenceRepository.count() >= count) {
            return;
        }

        for (int i = 0; i < count; i++) {
            addAgence(fullAgenceRepository);
        }
    }

    private Agence addAgence(CrudRepository<Agence, Long> repository) {
        int suffix = (int) System.currentTimeMillis();

        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setVille("Tunis");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");

        Vehicule v1 = new Vehicule();
        v1.setImmatriculation("123 TUN " + suffix);
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setCategorie(CategorieVehicule.SUV);
        v1.setStatut(StatutVehicule.MAINTENANCE);
        v1.setTarifJournalier(new BigDecimal("100"));

        Vehicule v2 = new Vehicule();
        v2.setImmatriculation("234 TUN " + (suffix + 1));
        v2.setMarque("Isuzu");
        v2.setModele("DMax");
        v2.setCategorie(CategorieVehicule.SUV);
        v2.setStatut(StatutVehicule.MAINTENANCE);
        v2.setTarifJournalier(new BigDecimal("100"));

        v1.setAgence(agence);
        v2.setAgence(agence);
        agence.getVehicules().add(v1);
        agence.getVehicules().add(v2);

        return repository.save(agence);
    }

    private void loadAgence(String repositoryType, CrudRepository<Agence, Long> repository) {
        StringBuilder sb = new StringBuilder();
        repository.findAll().forEach(agence -> {
            sb.append(repositoryType).append('\n');
            sb.append(agence.getIdAgence()).append(" | ").append(agence.getNom()).append('\n');
            sb.append("Vehicules Count : ").append(agence.getVehicules().size()).append('\n');
            agence.getVehicules().forEach(vehicule -> sb.append("=== ")
                    .append(vehicule.getIdVehicule())
                    .append('|')
                    .append(vehicule.getImmatriculation())
                    .append('\n'));
        });

        System.out.println(sb);
        assertTrue(sb.length() > 0);
    }
}

