# ADSS GROUP K Workers Module

## Development

The `dev` directory contains a Maven project.
If you use IntelliJ, use the following command.
If you use something else, you better know how to use Maven with it.

```
cd dev
mvn idea:idea
```

An IntelliJ project file should now be present in the `dev` directory.
Please don't commit IDE configurations to the repository.

### Packaging

Create a JAR by:

```
mvn clean package shade:shade
```

Running the JAR can be done with:

```
java -jar ./target/`workers-0.0.1.jar
```