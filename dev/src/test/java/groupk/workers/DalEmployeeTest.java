package groupk.workers;
import groupk.workers.data.DalController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DalEmployeeTest {
    @Test
    public void addDB(){
        DalController dalController = new DalController();
        assertEquals(true, true);
    }
}
