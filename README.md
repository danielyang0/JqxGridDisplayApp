# JqxGridDisplayApp

## Usage

### Mysql In Docker 
create a contaner with mysql server running on port 3307
```console
docker run -p 3307:3306 --name titanic -e MYSQL_ROOT_PASSWORD=pass -d mysql:8.0 mysqld --default-authentication-plugin=mysql_native_password;
```
create a database called test manually in this mysql server

### pack to application
In the root folder:
```console
$mvn clean install
```
Or use the pre-packed .war file in the packed folder
### run the application
```console
$java -jar xxx.war
```
The application will be run on port 3000

### upload file from folder csvData
