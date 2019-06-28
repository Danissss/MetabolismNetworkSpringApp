mvn clean
mvn package
rm MetabolismNetwork-0.0.1-SNAPSHOT.jar
mv target/MetabolismNetwork-0.0.1-SNAPSHOT.jar .
java -jar MetabolismNetwork-0.0.1-SNAPSHOT.jar
