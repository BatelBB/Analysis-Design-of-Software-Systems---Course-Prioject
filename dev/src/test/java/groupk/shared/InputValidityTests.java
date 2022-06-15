package groupk.shared;

import static org.junit.jupiter.api.Assertions.*;

import groupk.shared.inputValidity.InputValidity;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class InputValidityTests {
    @Test
    public void testPhoneValidator() {
        String[] correct = {"050-1234567",
        "057-1234566", "052-7654321"};
        String[] incorrect = {"0555-243231", "050-123456", "150-1234567", "-1234567", "0501234567"};
        testPattern(InputValidity.phone, correct, incorrect);
    }

    private void testPattern(InputValidity.StringInputValidator validator,
                             String[] correct, String[] incorrect) {
        for(String s: correct) {
            assertTrue(validator.test(s), s + " shouldv'e matches but didn't");
        }

        for(String s: incorrect) {
            assertFalse(validator.test(s), s + " shouldn't have matches but did");
        }

    }
}
