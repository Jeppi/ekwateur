package model.consommation;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.client.Client;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class Consommation {
    private Client client;
    private LocalDate mois;

    private double electricite_kWh;
    private double gaz_kWh;
}
