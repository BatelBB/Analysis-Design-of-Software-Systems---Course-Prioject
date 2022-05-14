package groupk.workers.data;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DalController {
    public DalController(){
        File file = new File("EmployeeDB");
        if(!file.exists())
            createTable();
    }
    public void createTable(){

    }
}
