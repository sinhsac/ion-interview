rem clean data before
rem release to create new version
rem build project with production config
call gradlew.bat clean
call gradlew.bat release
call gradlew.bat assemble