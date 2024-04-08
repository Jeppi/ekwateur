package model.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
@AllArgsConstructor
public class ClientParticulier extends Client {

    private Civilite civilite;
    private String nom;
    private String prenom;

    public String toString() {
        return "- Compte particulier   réf: " + getReference() +
                ", client : " + civilite().getLabel() + " " + prenom() + " " + nom();
    }

    public enum Civilite {

        MONSIEUR(1, "M."),
        MADAME(2, "Mme");

        private final int value;
        private final String label;

        private Civilite(int value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public static Civilite findCiviliteByValue(int value) {
            for (Civilite civilite : Civilite.values()) {
                if (value == civilite.value) {
                    return civilite;
                }
            }
            return null;
        }
    }

}
