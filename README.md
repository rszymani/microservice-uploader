To start application:
- execute command `./mvnw package && java -jar target/fileloader-0.0.1-SNAPSHOT.jar`

This project is simple REST api which allows to:
- load users from file to H2 database (curl -F 'file=@file.csv' http://localhost:8080/saveUsers) - method POST with parameters as a path to file (@file.csv)
- read oldest user with number (curl -X GET -i http://localhost:8080/oldestUserWithNumber) - method GET
- read all user saved in database (curl -X GET -i http://localhost:8080/allUsers) - method GET
- read number of users saved in database (curl -X GET -i http://localhost:8080//countUsers) - method GET
- read user by last name (curl -X GET -i 'http://localhost:8080//usersByLastName/{lastName}') - method GET with last name in URI
- delete user with id (curl -X DELETE -i http://localhost:8080/deleteUser/{id}) - method DELETE with id in URI
- delete multiple users with ids (curl -X DELETE -i 'http://localhost:8080/deleteUser/{id_1},{id_2}') - method DELETE with ids separated by comma in URI


