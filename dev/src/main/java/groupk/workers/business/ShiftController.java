package groupk.workers.business;

import groupk.workers.data.Employee;
import groupk.workers.data.Shift;
import groupk.workers.data.ShiftRepository;

import java.util.*;

public class ShiftController {
    private ShiftRepository repo;

    public ShiftController(){
        repo = new ShiftRepository();
    }

    public Shift addShifts(groupk.workers.data.Shift shift){
        if(!ifShiftExist(shift)) {
            HashMap<Employee.Role, Integer> numOfRoles = new HashMap<>();
            for (Employee.Role r : Employee.Role.values())
                numOfRoles.put(r, 0);
            for(Employee e :shift.getStaff())
                numOfRoles.replace(e.getRole(), numOfRoles.get(e.getRole())+1);
            for(Employee.Role r : shift.getRequiredStaff().keySet()){
                if(shift.getRequiredStaff().get(r) > numOfRoles.get(r))
                    throw new IllegalArgumentException("There are not enough workers to open this shift.");
            }
            return repo.addShift(shift);
        }
        else
            throw new IllegalArgumentException("Shift already exists.");
    }

    public Shift getShift(Calendar date, Shift.Type type){
        LinkedList<Shift> shifts = repo.getShifts();
        for (Shift s: shifts) {
            if(s.getDate().equals(date) && s.getType().equals(type))
                return s;
        }
        throw new IllegalArgumentException("Shift does not exists.");
    }

    public boolean ifShiftExist(Shift shift){
        LinkedList<Shift> shifts = repo.getShifts();
        for (Shift s: shifts) {
            if(s.getDate().equals(shift.getDate()) && s.getType().equals(shift.getType()))
                return true;
        }
        return false;
    }

    public List<Shift> listShifts(){
        return new ArrayList<>(repo.getShifts());
    }

    public Shift setRequiredRoleInShift(Calendar date, Shift.Type type, Employee.Role role, int count) {
        return getShift(date, type).setRequiredRoleInShift(role, count);
    }

    public Shift setRequiredStaffInShift(Calendar date, Shift.Type type, HashMap<Employee.Role, Integer> requiredStaff) {
        return getShift(date, type).setRequiredStaff(requiredStaff);
    }

}
