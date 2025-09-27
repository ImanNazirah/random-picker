java -jar target/random-picker-0.0.1-SNAPSHOT.jar

# Build the app
#./mvnw clean package
#
## The JAR file you expect to run (change this to your actual JAR name pattern)
#JAR_PATTERN="target/*.jar"
#
## Wait until a JAR file appears in the target folder
#echo "Waiting for build to finish and JAR to be created..."
#
#while [ -z "$(ls $JAR_PATTERN 2>/dev/null)" ]; do
#  sleep 1
#done
#
## Get the actual JAR file name (first match)
#JAR_FILE=$(ls $JAR_PATTERN | head -n 1)
#
#echo "Found JAR: $JAR_FILE"
#echo "Starting app..."
#
#java -jar "$JAR_FILE"