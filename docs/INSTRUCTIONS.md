# ADSS Group K Employees and Deliveries Instructions

## Quick Start

Once the program starts, you will be greeted with a prompt for commands.

For general help and command list, type the `help` command.
For help about a specific command, type said command without arguments.
For example, for `create employee`, input `create employee` for help.


To login:

```
login 999871163
```

Once logged in, you are authorized to perform commands, such as:

```
get employee 999871163
```

For more information on available commands, refer to the Commands section of this document.

## Commands

The following commands are supported:

* `login`.
* `quit`.
* `create employee`.
* `get employee`.
* `delete employee`.
* `list employees`.
* `add shift preference`.
* `delete shift preference`.
* `create shift`.
* `add shift staff`.
* `delete shift staff`.
* `update shift required role`.
* `list shifts`.
* `can work`.

All example commands should work when using the provided sample data (`load sample` command).

### Login

Set the currently active user.

As of now, employees are authenticated only by their ID.
Not using passwords allows to use the service with authentication methods not involving passwords, such as TOTP or hardware keys.

Any user, including unauthenticated users, can perform this command.

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

Any user, including unauthenticated users, can perform this command.

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

Any user, including unauthenticated users, can perform this command.

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

Authenticated employees can look themselves up, employees authenticated as HumanResources role can look up all employees.

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

Only employees authenticated as HumanResources role may invoke this command.

#### Usage

```
delete employee <id>
```

#### Example

```
delete employee 999356934
```

### List Employees

List employees and their roles.

Lists in CSV format for easy import to Excel, to allow incremental rollout of the new system.

Only employees authenticated as HumanResources role may invoke this command.

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

Employees can only invoke this command for themselves.

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

Employees can only invoke this command for themselves.

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

It is recommended to use the `can work` command to pick employees for a shift before creating it.

Only employees authenticated as HumanResources role may invoke this command.

#### Usage

```
create shift <date> <type> [staff]
```

Where `type` is one of the following:

- `Morning`.
- `Evening`.

#### Example

```
create shift 2022-04-24 Evening 999368814 999849854 999481773 999205214 999072804
```

### Add Shift Staff

Add an employee to a shift's staff.

Only employees authenticated as HumanResources role may invoke this command.

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

Only employees authenticated as HumanResources role may invoke this command.

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

Only employees authenticated as HumanResources role may invoke this command.

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

### List Shifts

List all past and future shifts.

Lists shifts in YAML format, for easy parsing in printing scripts. Allowing the customer to incrementally roll out the new system by partially working with paper forms.

Only employees authenticated as HumanResources role may invoke this command.

#### Usage

```
list shifts
```

#### Example

```
list shifts
```

### Can Work

List employees who can work a specified shift, optionally filter employees of a certain role.

Lists in CSV format for easy import to Excel, to allow incremental rollout of the new system.

Only employees authenticated as HumanResources role may invoke this command.

#### Usage

```
can work <date> <type>
```

```
can work <date> <type> <role>
```

#### Example

```
can work 2022-04-24 Evening
```

```
can work 2022-04-24 Evening Logistics
```
