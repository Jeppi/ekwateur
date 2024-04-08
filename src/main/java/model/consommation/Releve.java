package model.consommation;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.client.Client;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public final class Releve {
    private Client client;
    private LocalDate date;
    private double kWh_electricite;
    private double kWh_gaz;

}
