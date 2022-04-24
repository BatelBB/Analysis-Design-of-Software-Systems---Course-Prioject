# ADSS Group K Employees TUI Instructions

## Quick Start

Once the program starts, you will be greeted with a prompt for commands.

For general help and command list, type any unknown command.
For help about a specific command, type said command without arguments.
For example, for `create employee`, input `create employee` for help.

First thing you should do is load the sample data:

```
load sample
```

Once sample data loads, login as an HR user:

```
login 999871163
```

Once logged in, you are authorized to perform commands, such as:

```
get employee 999871163
```

For more information on available commands, refer to the Commands section of this document.

## Commands

All example commands should work when using the provided sample data (`load sample` command).

### Load Sample

Load sample data for demonstration purposes.

#### Usage

```
load sample
```

#### Example

```
load sample
```

### Login

Set the currently active user.

As of now, employees are authenticated only by their ID.
Not using passwords allows to use the service with authentication methods not involving passwords, such as TOTP or hardware keys.

#### Usage

```
login <id>
```

#### Example

```
login 999871163
```

### Quit

Quit the program.

#### Usage

```
quit
```

#### Example

```
quit
```

### Create Employee

Add a new employee.

#### Usage

```
create employee <name> <id> <bank> <bank-id> <bank-branch> <salary-per-hour> <role>
```

Where `role` is one of the following:

- `Logistics`.
- `HumanResources`.
- `Stocker`.
- `Cashier`.
- `LogisticsManager`.
- `ShiftManager`.
- `Driver`.
- `StoreManager`.

#### Example

```
create employee Noga 999074396 Bar 12345 1 30 HumanResources
```

### Get Employee

Get the details of an employee.

#### Usage

```
get employee <id>
```

#### Example

```
get employee 999871163
```

### Delete Employee

Delete an employee.

#### Usage

```
delete employee <id>
```

#### Example

```
delete employee 999356934
```

### Update Employee

Update an employee's details.

#### Usage

```
update employee <id> <name> <bank> <bank-id> <bank-branch> <salary-per-hour> <role>
```

Where `role` is one of the following:

- `Logistics`.
- `HumanResources`.
- `Stocker`.
- `Cashier`.
- `LogisticsManager`.
- `ShiftManager`.
- `Driver`.
- `StoreManager`.

#### Example

```
update employee 999356934 Noga Bar 12345 1 30 HumanResources
```

### List Employees

List employees and their roles.

#### Usage

```
list employees
```

#### Example

```
list employees
```

### Add Shift Preference

Add an available shift for an employee.

#### Usage

```
add shift preference <id> <shift>
```

Where `shift` is one of the following:

- `SundayMorning`.
- `SundayEvening`.
- `MondayMorning`.
- `MondayEvening`.
- `TuesdayMorning`.
- `TuesdayEvening`.
- `WednesdayMorning`.
- `WednesdayEvening`.
- `ThursdayMorning`.
- `ThursdayEvening`.
- `FridayMorning`.
- `FridayEvening`.
- `SaturdayMorning`.
- `SaturdayEvening`.

#### Example

```
add shift preference 999871163 FridayEvening
```

### Delete Shift Preference

Remove an available shift for an employee.

#### Usage

```
delete shift preference <id> <shift>
```

Where `shift` is one of the following:

- `SundayMorning`.
- `SundayEvening`.
- `MondayMorning`.
- `MondayEvening`.
- `TuesdayMorning`.
- `TuesdayEvening`.
- `WednesdayMorning`.
- `WednesdayEvening`.
- `ThursdayMorning`.
- `ThursdayEvening`.
- `FridayMorning`.
- `FridayEvening`.
- `SaturdayMorning`.
- `SaturdayEvening`.

#### Example

```
delete shift preference 999871163 FridayEvening
```

### Create Shift

Create a shift.

#### Usage

```
create shift <date> <type> [staff]
```

Where `type` is one of the following:

- `Morning`.
- `Evening`.

#### Example

```
create shift 2022-04-24 Evening 999368814 999849854 999481773 999205214
```

### Add Shift Staff

Add an employee to a shift's staff.

#### Usage

```
add shift staff <date> <type> <employee-id>
```

Where `type` is one of the following:

- `Morning`.
- `Evening`.

#### Example

```
add shift staff 2022-04-24 Evening 999871163
```

### Delete Shift Staff

Remove an employee from a shift's staff.

#### Usage

```
delete shift staff <date> <type> <employee-id>
```

Where `type` is one of the following:

- `Morning`.
- `Evening`.

#### Example

```
delete shift staff 2022-04-24 Evening 999871163
```

### Update Required Role In Shift

Update a shift's required roles.

#### Usage

```
update shift required role <date> <type> <role> <required-count>
```

Where `type` is one of the following:

- `Morning`.
- `Evening`.

#### Example

```
update shift required role 2022-04-24 Evening Stocker 2
```
