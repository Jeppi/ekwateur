package application;

import model.client.Client;
import model.client.ClientParticulier;
import model.client.ClientPro;
import model.consommation.Consommation;

public class Facturation {

    public final static double PRIX_KWH_ELECTRICITE_PARTICULIER = .121;
    public final static double PRIX_KWH_GAZ_PARTICULIER = .115;

    public final static double PRIX_KWH_ELECTRICITE_PRO_REDUIT = .114;
    public final static double PRIX_KWH_GAZ_PRO_REDUIT = .111;
    public final static double PRIX_KWH_ELECTRICITE_PRO = .118;
    public final static double PRIX_KWH_GAZ_PRO = .113;

    public final static long CA_SEUIL = 1_000_000;

    public static double montantAFacturer(Consommation consommation) {
        return montantAFacturer(consommation.getClient(),
                consommation.getElectricite_kWh(),
                consommation.getGaz_kWh());
    }

    public static double montantAFacturer(Client client, double kWh_electricite_consomme, double kWh_gaz_consomme) {
        double montant = 0;

        if (client instanceof ClientPro) {
            if (((ClientPro) client).ca() < CA_SEUIL) {
                montant = kWh_electricite_consomme * PRIX_KWH_ELECTRICITE_PRO
                        + kWh_gaz_consomme * PRIX_KWH_GAZ_PRO;
            } else {
                montant = kWh_electricite_consomme * PRIX_KWH_ELECTRICITE_PRO_REDUIT
                        + kWh_gaz_consomme * PRIX_KWH_GAZ_PRO_REDUIT;
            }
        } else if (client instanceof ClientParticulier) {
            montant = kWh_electricite_consomme * PRIX_KWH_ELECTRICITE_PARTICULIER
                    + kWh_gaz_consomme * PRIX_KWH_GAZ_PARTICULIER;
        }

        return montant;
    }

}
