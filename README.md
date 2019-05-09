### How to start
1. Compile app `# mvnw clean install`
2. Start app locally `# java -jar ./target/test-task-0.0.1-SNAPSHOT.jar`

### API documentation
Link on the swagger documentation - http://localhost:8080/swagger-ui.html

### Description
By default app configured to use Windows based Downloads folder as a sandbox - `C:\Users\User\Downloads`.
It could be changed in `/src/main/resources/application.properties` file.

### Examples
1. GET `http://localhost:8080/dir/subdirectories?name=test-task` 
```json
{
  "directory" : {
    "path" : "test-task",
    "filesCount" : 5
  },
  "subDirectories" : [ {
    "path" : "test-task\\.mvn",
    "filesCount" : 0
  }, {
    "path" : "test-task\\src",
    "filesCount" : 0
  } ]
}
```

2. GET `http://localhost:8080/dir/files?path=test-task`
```json
{
  "path" : "test-task",
  "files" : [ ".gitignore", "HELP.md", "mvnw", "mvnw.cmd", "pom.xml" ]
}
```

3 GET `http://localhost:8080/file/attributes?path=test-task%5CHELP.md`
```json
{
  "directory" : false,
  "hidden" : false,
  "readOnly" : false,
  "regularFile" : true,
  "symbolicLink" : false,
  "archive" : false,
  "other" : false,
  "system" : false
}
```