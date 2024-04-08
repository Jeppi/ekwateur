package model.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void testCreationReferenceClient() {

        String reference_1 = Client.Reference.getNewReference();
        String reference_2 = Client.Reference.getNewReference();
        Assertions.assertNotEquals(reference_1, reference_2);

        Assertions.assertEquals(11, reference_1.length());
        Assertions.assertEquals(11, reference_2.length());

        Assertions.assertEquals("EKW", reference_1.substring(0, 3));
        Assertions.assertEquals("EKW", reference_2.substring(0, 3));

    }
}
