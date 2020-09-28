# ION Interview

## Usage
* Run `gradlew bootRun` to start server.
* Run `gradlew test` for testing.
* Run `gradlew.bat release` to make a release for deployment.
* Finally, run `release.bat` for all upper command


## Deployment using docker
* Run command `docker build -t ion-interview/server-docker .` to build the docker
* Run command `docker run -p 8088:8080 tnt/server-docker` to start server.
