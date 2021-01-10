#Steps to rename project

1. Remove `.git/` folder
2. Rename artifactId, ProjectName in `Pom.xml`
3. Rename package name using shortcut `shift+f6` in Intellij
4. Set cassandra model directory in `CassandraConfiguration.kt` at method `getEntityBasePackages()`
5. Set properties `[MICROSERVICE_URL_NAME, MICROSERVICE_NAME, BASE_URL, CASSANDRA_NODES]` in `AppProperties.kt`
6. Remove .idea and .iml folder and file respectively
7. Close Intellij, Rename main folder name and open project in Intellij.
8. Now Push top new git repo