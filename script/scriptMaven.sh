docker run -it --rm --name maven --network=reseau -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven maven:3.5.0-jdk-8 mvn clean install
