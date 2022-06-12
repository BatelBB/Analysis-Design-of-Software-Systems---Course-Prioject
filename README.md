# ADSS GROUP K Workers And Logistics Module

## IDs

| Name             | ID          |
|------------------|-------------|
| Batel Shkolnik   | `209153709` |
| Yuval Dolev      | `208631051` |
| Michael Tzahi    | `208612812` |
| Shoham Cohen     | `207546078` |
| Miri Volozhinsky | `207496373` |
| Lior Kummer      | `208231027` |
| Ido Shapira      | `319034138` |
| Tamir Avisar     | `211498621` |

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
