Add both jar files from lib directory:

mvn install:install-file -Dfile=/your/path/to/lib/TicTacTac.jar -DgroupId=TicTacToe -DartifactId=TicTacToeJar -Dversion=1.0.0 -Dpackaging=jar


mvn install:install-file -Dfile=/your/path/to/lib/java_server.jar -DgroupId=TakaServer -DartifactId=TakaServerJar -Dversion=1.0.0 -Dpackaging=jar

To test use maven:

mvn test

To run the server, run from the root:

java -cp out/artifacts/java_server_ttt_jar/java_server_ttt.jar Main.Main
