# ADSS GROUP K Workers And Logistics Module

A project that was developed during a course in the 2nd year of my Software Engineering degree.
A team of eight people, designing and developing a system to manage a food store.

## Development

For IntelliJ users:

```
git clean -d -X --force
cd dev
mvn idea:idea
```

Double click on `dev/adss.ipr` to open the IntelliJ project.

### Packaging

Build a Jar:

```
mvn clean package shade:shade
```

Run the Jar:

```
java -jar ./target/`adss-0.0.1.jar
```
