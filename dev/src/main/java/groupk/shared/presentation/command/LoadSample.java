package groupk.shared.presentation.command;

import groupk.shared.presentation.CommandRunner;
import groupk.shared.service.dto.Employee;

import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

public class LoadSample implements Command {
    @Override
    public String name() {
        return "load sample";
    }

    @Override
    public String description() {
        return "insert sample data";
    }

    @Override
    public boolean isMatching(String line) {
        return line.startsWith("load sample");
    }

    @Override
    public void execute(String[] command, CommandRunner runner) {
        if (command.length != 2) {
            System.out.println("Error: Too many arguments.");
            System.out.println("Usage:");
            System.out.println("> load sample");
            return;
        }

        System.out.println("Hang on! This might take a minute on slower drives.");

        Set<Employee.ShiftDateTime> all = new LinkedHashSet<Employee.ShiftDateTime>();
        all.add(Employee.ShiftDateTime.SundayMorning);
        all.add(Employee.ShiftDateTime.SundayEvening);
        all.add(Employee.ShiftDateTime.MondayMorning);
        all.add(Employee.ShiftDateTime.MondayEvening);
        all.add(Employee.ShiftDateTime.TuesdayMorning);
        all.add(Employee.ShiftDateTime.TuesdayEvening);
        all.add(Employee.ShiftDateTime.WednesdayMorning);
        all.add(Employee.ShiftDateTime.WednesdayEvening);
        all.add(Employee.ShiftDateTime.ThursdayMorning);
        all.add(Employee.ShiftDateTime.ThursdayEvening);
        all.add(Employee.ShiftDateTime.FridayMorning);
        all.add(Employee.ShiftDateTime.FridayEvening);
        all.add(Employee.ShiftDateTime.SaturdayMorning);
        all.add(Employee.ShiftDateTime.SaturdayEvening);
        HashMap<Employee.Role, Integer> r1 = new HashMap<>();
        for(Employee.Role role : Employee.Role.values())
            r1.put(role, 0);
            r2.put(role, 0);

        String[] source = {"tamirHouse","batYam","0543397995","tamirStr","13","2","3","center"};
        String[]  destination = {"idoHouse","herzliya","0524321231","idoStr","100","1","6","center"};
        List<String[]> sources = new LinkedList<>();
        List<String[]> destinations = new LinkedList<>();
        sources.add(source);
        destinations.add(destination);
        List<Map<String,Integer>> products = new LinkedList<>();
        Map<String,Integer> product = new ConcurrentHashMap<>();
        product.put("Eggs_4902505139314",2);
        products.add(product);

        String[] source2 = {"miri","haifa","0522226668","miriSTR","13","2","3","north"};
        String[]  destination2 = {"lior","beersheva","0536545648","liorSTR","100","1","6","sourh"};
        List<String[]> sources2 = new LinkedList<>();
        List<String[]> destinations2 = new LinkedList<>();
        sources2.add(source2);
        destinations2.add(destination2);
        List<Map<String,Integer>> products2 = new LinkedList<>();
        Map<String,Integer> product2 = new ConcurrentHashMap<>();
        product.put("Water_7290019056966",2);
        products.add(product);

        // HR.
        runner.getService().createEmployee(
                "Avi",
                "111",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.HumanResources,
                all);
        runner.getService().createEmployee(
                "Eli",
                "112",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.HumanResources,
                all);
        runner.getService().createEmployee(
                "Dana",
                "113",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.HumanResources,
                all);
        runner.getService().createEmployee(
                "Noa",
                "114",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.HumanResources,
                all);

        // Cashiers.
        runner.getService().createEmployee(
                "Eli",
                "212",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Cashier,
                all);
        runner.getService().createEmployee(
                "Noa",
                "214",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Cashier,
                all);

        // Drivers:
        runner.getService().createEmployee(
                "Avi",
                "311",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Driver,
                all);
        runner.getService().createEmployee(
                "Dana",
                "313",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Driver,
                all);

        // Logistics:
        runner.getService().createEmployee(
                "Eli",
                "412",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Logistics,
                all);
        runner.getService().createEmployee(
                "Noa",
                "414",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Logistics,
                all);

        // Logistics Managers:
        runner.getService().createEmployee(
                "Eli",
                "512",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.LogisticsManager,
                all);
        runner.getService().createEmployee(
                "Noa",
                "514",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.LogisticsManager,
                all);

        // Shift Managers:
        runner.getService().createEmployee(
                "Eli",
                "612",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                all);
        runner.getService().createEmployee(
                "Noa",
                "614",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.ShiftManager,
                all);

        // Stockers:
        runner.getService().createEmployee(
                "Avi",
                "711",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Stocker,
                all);
        runner.getService().createEmployee(
                "Dana",
                "713",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.Stocker,
                all);

        // Store Managers:
        runner.getService().createEmployee(
                "Avi",
                "811",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.StoreManager,
                all);
        runner.getService().createEmployee(
                "Dana",
                "813",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.StoreManager,
                all);

        // Store Managers:
        runner.getService().createEmployee(
                "Avi",
                "911",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                all);
        runner.getService().createEmployee(
                "Dana",
                "913",
                "Example",
                1,
                1, new GregorianCalendar(),
                30,
                0,
                0,
                Employee.Role.TruckingManger,
                all);

        
        //create shifts
          runner.getService().addShift(
                  111,
                   new GregorianCalendar(2023, Calendar.APRIL, 21),
                   Shift.Type.Evening,
                     new LinkedList<>(),
                  r1 );

        runner.getService().addShift(
                  112,
                   new GregorianCalendar(2023, Calendar.APRIL, 23),
                   Shift.Type.Evening,
                     new LinkedList<>(),
                    r2 );


         //create Employee shift prefernces


        runner.getService().addEmployeeShiftPreference(
                311,
                311,
                Employee.ShiftDateTime.FridayEvening);

        runner.getService().addEmployeeShiftPreference(
                313,
                313,
                Employee.ShiftDateTime.SundayEvening);

        runner.getService().addEmployeeShiftPreference(
                911,
                911,
                Employee.ShiftDateTime.FridayEvening);

        runner.getService().addEmployeeShiftPreference(
                913,
                913,
                Employee.ShiftDateTime.SundayEvening);

        runner.getService().addEmployeeShiftPreference(
                412,
                412,
                Employee.ShiftDateTime.FridayEvening);

        runner.getService().addEmployeeShiftPreference(
                414,
                414,
                Employee.ShiftDateTime.SundayEvening);



        //add employees to shifts
        
        runner.getService().addEmployeeToShift(111,
         (2023, Calendar.APRIL, 21), 
         Shift.Type.Evening,
         "311");
''
         runner.getService().addEmployeeToShift(111,
         (2023, Calendar.APRIL, 21), 
         Shift.Type.Evening,
         "911");

         runner.getService().addEmployeeToShift(111,
         (2023, Calendar.APRIL, 21), 
         Shift.Type.Evening,
         "412");

         runner.getService().addEmployeeToShift(112,
         (2023, Calendar.APRIL, 23), 
         Shift.Type.Evening,
         "313");

         runner.getService().addEmployeeToShift(112,
         (2023, Calendar.APRIL, 23), 
         Shift.Type.Evening,
         "913");

         runner.getService().addEmployeeToShift(112,
         (2023, Calendar.APRIL, 23), 
         Shift.Type.Evening,
         "414");


         //add vehicles

        runner.getService().addVehicle(
                "B",
                "12315678",
                "mercedes",
                4,
                32);

        runner.getService().addVehicle(
                "C",
                "12345678",
                "volvo",
                8,
                22);


        
         //add licences

         runner.getService().addLicense(
                311,
         DLicense.B));


         runner.getService().addLicense(
                313,
                 DLicense.C));



        //add Truckings7

         runner.getService().addTrucking(
                913,
                "12345678",
                (2023, Calendar.APRIL, 23), 
                313,
                sources,
                destinations,
                products,
                2,
                0);


         runner.getService().addTrucking(
                911,
                "12315678",
                (2023, Calendar.APRIL, 21), 
                311,
                sources2,
                destinations2,
                products2,
                1,
                0);

                
                        // TODO Add more sample data.
    }
}
