package application;

import model.client.Client;
import model.consommation.Consommation;
import model.consommation.Releve;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;

public class Fournisseur {

    private Map<Client, List<Releve>> releves;

    public Fournisseur() {
        releves = new HashMap<>();
    }

    public void ajouter_client(Client client) {
        List<Releve> releves_client = new ArrayList<>();
        releves.put(client, releves_client);
    }

    public void ajouter_releve(Releve releve) {
        Client client = releve.getClient();
        List<Releve> releves_client;

        if (releves.containsKey(client)) {
            releves_client = releves.get(releve.getClient());
            if (!valide(releve, releves_client)) {
                System.out.println("Ce relevé n'est pas postérieur aux précédents ou");
                System.out.println("la valeur des compteurs n'est pas supérieure ou égale aux valeurs précédentes");
                return;
            }
        } else {
            releves_client = new ArrayList<>();
            releves.put(client, releves_client);
        }

        releves_client.add(releve);
    }

    private boolean valide(Releve releve, List<Releve> releves) {
        if (releves.isEmpty()) {
            return true;
        }

        Releve dernier_releve = releves.stream()
                .max(comparing(Releve::getDate))
                .get();

        return releve.getDate().isAfter(dernier_releve.getDate())
                && releve.getKWh_electricite() >= dernier_releve.getKWh_electricite()
                && releve.getKWh_gaz() >= dernier_releve.getKWh_gaz();
    }

    public List<Releve> getReleves(Client client) {
        if (!releves.containsKey(client)) {
            throw new RuntimeException("Ce client n'existe pas");
        }
        return releves.get(client);
    }

    public List<Client> getClients() {
        return releves.keySet().stream()
                .toList();
    }

    public Consommation calcule_conso(Client client, LocalDate date) {
        List<Releve> releves_client = getReleves(client);
        LocalDate mois = date.withDayOfMonth(1);

        Releve releve_initial = releves_client.stream()
                .filter(releve -> mois.isAfter(releve.getDate()))
                .max(comparing(Releve::getDate))
                .orElse(new Releve(client, null, 0d, 0d));

        Releve releve_final = releves_client.stream()
                .filter(releve -> date.getYear() == releve.getDate().getYear()
                        && date.getMonthValue() == releve.getDate().getMonthValue())
                .max(comparing(Releve::getDate))
                .orElse(releve_initial);

        return new Consommation(client, mois,
                releve_final.getKWh_electricite() - releve_initial.getKWh_electricite(),
                releve_final.getKWh_gaz() - releve_initial.getKWh_gaz());
    }


    public Client getClientByRef(String clientRef) {
        for (Client client : getClients()) {
            if (clientRef.equals(client.getReference())) {
                return client;
            }
        }
        return null;
    }

}
