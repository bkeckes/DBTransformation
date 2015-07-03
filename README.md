# Tranforms relational database to Neo4J database

Supported relational databases:
* mysql
* oracle12


## Usage
* Insert your database configuration into `config.properties` 
* run `mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.2 -Dpackaging=jar -Dfile=ojdbc7.jar -DgeneratePom=true`
* run `mvn clean package` to compile
* start relational database (don´t start neo4j. It will start automatically)
* run `java -cp target\DBTransformation-0.0.1-SNAPSHOT.jar de.bkdev.transformation.Transformer` to start Application
