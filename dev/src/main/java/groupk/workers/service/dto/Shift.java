package groupk.workers.service.dto;

import java.util.Date;

public class Shift {
    public enum Type {
        Morning,
        Evening
    }

    Employee[] staff;
    Date date;
    Type type;

    public Type getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }
}
