package model.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
@AllArgsConstructor
public class ClientPro extends Client {
    private String num_siret;
    private String raison_sociale;
    private long ca;

    public String toString() {
        return "- Compte professionnel réf: " + getReference() +
                ", société : " + raison_sociale() + ", n° siret : " +
                num_siret() + ", CA : " + ca() + " €";
    }

}
