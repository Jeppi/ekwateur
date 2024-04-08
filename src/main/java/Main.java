import application.Facturation;
import application.Fournisseur;
import input.LectureClavier;
import model.client.Client;
import model.client.ClientParticulier;
import model.client.ClientPro;
import model.consommation.Releve;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Initialisation
        Fournisseur fournisseur = new Fournisseur();
        LocalDate date_courante = LocalDate.now();
        int mois_courant = date_courante.getMonthValue();
        int annee_courante = date_courante.getYear();
        Client client = null;

        // On ajoute des clients
        ClientPro clientPro = new ClientPro("siretNb", "EntrepriseLambda", 10_111_000);
        ClientParticulier clientParticulier = new ClientParticulier(ClientParticulier.Civilite.MONSIEUR,
                "Ribeiro", "Jean-Paul");

        // On ajoute des relevés
        fournisseur.ajouter_releve(new Releve(clientPro, LocalDate.of(2023, 5, 25), 10000, 4000));
        fournisseur.ajouter_releve(new Releve(clientPro, LocalDate.of(2023, 6, 25), 17000, 9000));
        fournisseur.ajouter_releve(new Releve(clientPro, LocalDate.of(2023, 7, 25), 21000, 13000));
        fournisseur.ajouter_releve(new Releve(clientPro, LocalDate.of(2023, 8, 25), 32000, 14000));
        fournisseur.ajouter_releve(new Releve(clientPro, LocalDate.of(2023, 9, 25), 40000, 18000));
        fournisseur.ajouter_releve(new Releve(clientPro, LocalDate.of(2023, 10, 25), 50000, 24000));
        fournisseur.ajouter_releve(new Releve(clientPro, LocalDate.of(2023, 11, 25), 66000, 26000));
        fournisseur.ajouter_releve(new Releve(clientPro, LocalDate.of(2023, 12, 25), 71000, 31000));
        fournisseur.ajouter_releve(new Releve(clientPro, LocalDate.of(2024, 1, 26), 71000, 31000));
        fournisseur.ajouter_releve(new Releve(clientPro, LocalDate.of(2024, 2, 25), 79000, 35000));

        fournisseur.ajouter_client(clientParticulier);


        // On affiche la liste des clients
        afficher("Voici la liste des clients :");
        afficherClients(fournisseur);

        while (true) {
            afficher("Saisissez :");
            afficher("- 1 pour créer un nouveau client");
            afficher("- 2 pour selectionner un client");

            int choix = LectureClavier.lireInt(1, 2);

            if (choix == 1) {
                afficher("Saisissez 1 pour un clientPro Pro, 2 pour un clientPro particulier");
                int choix_type = LectureClavier.lireInt();
                if (choix_type == 1) {
                    afficher("Saisissez le n° de Siret");
                    String siret = LectureClavier.lireString();
                    afficher("Saisissez la raision sociale");
                    String raison = LectureClavier.lireString();
                    afficher("Saisissez le montant du chiffre d'affaire");
                    long ca = LectureClavier.lireLong();
                    client = new ClientPro(siret, raison, ca);
                } else if (choix_type == 2) {
                    afficher("Saisissez 1 pour monsieur, 2 pour madame");
                    int civ = LectureClavier.lireInt();
                    ClientParticulier.Civilite civilite = ClientParticulier.Civilite.findCiviliteByValue(civ);
                    afficher("Saisissez le nom");
                    String nom = LectureClavier.lireString();
                    afficher("Saisissez le prenom");
                    String prenom = LectureClavier.lireString();
                    client = new ClientParticulier(civilite, nom, prenom);
                }
                if (client != null) {
                    fournisseur.ajouter_client(client);
                } else {
                    continue;
                }
            }

            while (true) {
                // On choisit un client :
                afficher("-> Saisissez une référence client, voici la liste (q pour revenir en arrière) : ");
                afficherClients(fournisseur);
                String clientRef = LectureClavier.lireString();
                if ("q".equals(clientRef)) {
                    break;
                }

                Client client_choisi = fournisseur.getClientByRef(clientRef);

                while (true) {
                    // On affiche les relevés de consommation disponibles
                    afficher("Voici la liste des relevés du client choisi : ");
                    afficher(fournisseur.getReleves(client_choisi));

                    afficher("-> Pour ajouter un relevé à ce client, saisissez 1 (vous pouvez ajouter un relevé par jour),");
                    afficher("saisissez 2 pour passer au choix du mois à facturer ");
                    afficher("en fonction des relevés de la liste.");
                    if (LectureClavier.lireInt(1, 2) == 2) {
                        break;
                    }

                    // On ajoute un relevé pour ce client
                    afficher("Date de relevé: (AAAAMMJJ) :");
                    LocalDate date = LectureClavier.lireDate();
                    afficher("Valeur compteur electrique en kWh ");
                    double elec = LectureClavier.lireDouble();
                    afficher("Valeur compteur gaz en kWh ");
                    double gaz = LectureClavier.lireDouble();

                    Releve nouveau_releve = new Releve(client_choisi, date, elec, gaz);
                    fournisseur.ajouter_releve(nouveau_releve);
                }

                while (true) {
                    // On choisi le mois à facturer
                    afficher("-> Sélectionner un mois à facturer de 1 à 12 sur les 12 derniers mois (mois courant inclus)");
                    afficher("0 pour revenir au choix précédent");
                    int mois_conso = LectureClavier.lireInt();
                    if (mois_conso == 0) {
                        break;
                    }
                    int annee_conso = annee_courante;
                    if (mois_conso > mois_courant) {
                        annee_conso = annee_courante - 1;
                    }
                    LocalDate date_conso = LocalDate.of(annee_conso, mois_conso, 1);

                    double conso_mois = Facturation.montantAFacturer(fournisseur.calcule_conso(client_choisi, date_conso));
                    afficher("La montant à facturer pour le mois de " + date_conso.getMonth().name() + " est de : " + conso_mois + " €");
                }

            }
        }
    }

    private static void afficher(String msg) {
        System.out.println(msg);
    }

    private static <T> void afficher(List<T> list) {
        if (list.isEmpty()) {
            afficher("- Cette liste est vide.");
        }
        for (T entity : list) {
            System.out.println(entity);
        }
    }

    private static void afficherClients(Fournisseur fournisseur) {
        List<Client> clients = fournisseur.getClients();
        for (Client c : clients) {
            System.out.println(c);
        }
    }
}
