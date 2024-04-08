package model.client;

import lombok.Data;

@Data
public abstract class Client {
    private String reference;

    public String getReference() {
        return reference;
    }

    public void setReference() {
        this.reference = Reference.getNewReference();
    }

    Client() {
        setReference();
    }

    public static class Reference {
        private static long dernier_client_ref = 10_000_000;
        private static final String CLIENT_PREFIX = "EKW";

        public static String getNewReference() {
            dernier_client_ref++;
            return formatReference(dernier_client_ref);
        }

        private static String formatReference(long numValue) {
            return CLIENT_PREFIX + numValue;
        }
    }
}
