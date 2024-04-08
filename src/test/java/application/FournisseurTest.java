package application;

import model.client.ClientParticulier;
import model.client.ClientPro;
import model.consommation.Releve;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class FournisseurTest {

    @Test
    public void test_ajout_releves() {
        Fournisseur fournisseur = new Fournisseur();

        // On ajoute des clients
        ClientPro clientPro = new ClientPro("siretNb", "nom", 1_111);
        ClientParticulier clientParticulier = new ClientParticulier(ClientParticulier.Civilite.MONSIEUR,
                "Ribeiro", "Jean-Paul");

        // On ajoute des relevés
        Releve releve_1_pro = new Releve(clientPro, LocalDate.of(2024, 1, 1), 10000, 4000);
        Releve releve_2_pro = new Releve(clientPro, LocalDate.of(2024, 2, 3), 20000, 9000);

        Releve releve_1_particulier = new Releve(clientParticulier, LocalDate.of(2024, 2, 3), 1000, 500);
        Releve releve_2_particulier = new Releve(clientParticulier, LocalDate.of(2024, 3, 3), 2000, 1000);

        fournisseur.ajouter_releve(releve_1_pro);
        fournisseur.ajouter_releve(releve_2_pro);

        fournisseur.ajouter_releve(releve_1_particulier);
        fournisseur.ajouter_releve(releve_2_particulier);

        List<Releve> releves_client_pro = fournisseur.getReleves(clientPro);
        Assertions.assertEquals(2, releves_client_pro.size());
        Assertions.assertEquals(releves_client_pro.get(0), releve_1_pro);
        Assertions.assertEquals(releves_client_pro.get(1), releve_2_pro);

        List<Releve> releves_client_particulier = fournisseur.getReleves(clientParticulier);
        Assertions.assertEquals(2, releves_client_particulier.size());
        Assertions.assertEquals(releves_client_particulier.get(0), releve_1_particulier);
        Assertions.assertEquals(releves_client_particulier.get(1), releve_2_particulier);

        // Ajout de releve non valide
        Releve releve_3_pro = new Releve(clientPro, LocalDate.of(2024, 4, 1), 100, 4000);
        fournisseur.ajouter_releve(releve_3_pro);
        Assertions.assertEquals(2, releves_client_pro.size());
    }

}
