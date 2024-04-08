package application;

import model.client.ClientParticulier;
import model.client.ClientPro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FacturationTest {

    @Test
    void testMontantAFacturer() {
        // prix pro
        ClientPro clientPro_bas_ca = new ClientPro("siretNb", "entreprie_lambda", Facturation.CA_SEUIL - 1);
        ClientPro clientPro_haut_ca = new ClientPro("siretNb", "entreprie_lambda", Facturation.CA_SEUIL);

        double montant_client_bas_ca = Facturation.montantAFacturer(clientPro_bas_ca, 2000, 1000);
        double calcul_client_bas_ca = 2000 * Facturation.PRIX_KWH_ELECTRICITE_PRO + 1000 * Facturation.PRIX_KWH_GAZ_PRO;
        Assertions.assertEquals(calcul_client_bas_ca, montant_client_bas_ca, 0.01);

        double montant_client_haut_ca = Facturation.montantAFacturer(clientPro_haut_ca, 2000, 1000);
        double calcul_client_haut_ca = 2000 * Facturation.PRIX_KWH_ELECTRICITE_PRO_REDUIT + 1000 * Facturation.PRIX_KWH_GAZ_PRO_REDUIT;
        Assertions.assertEquals(calcul_client_haut_ca, montant_client_haut_ca, 0.01);

        // prix particulier
        ClientParticulier clientParticulier = new ClientParticulier(ClientParticulier.Civilite.MONSIEUR, "Dupont", "Pierre");
        double montant_client_particulier = Facturation.montantAFacturer(clientParticulier, 200, 100);
        double calcul_client_particulier = 200 * Facturation.PRIX_KWH_ELECTRICITE_PARTICULIER + 100 * Facturation.PRIX_KWH_GAZ_PARTICULIER;
        Assertions.assertEquals(calcul_client_particulier, montant_client_particulier, 0.01);

    }

}
