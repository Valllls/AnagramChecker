# AnagramChecker

AnagramChecker is a Java program compiled and unit tested using the gradle build automation tool and the junit testing framework. 

## Overview
- Program takes in a particular UserQuery from the console, consisting of [username, first word, second word].
- If not seen before (i.e. not in the previously run results list) then the number of each character in either word is calculated. If equal then the words are anagrams
- The result of this query is then appended to a text file of previously run queries and their results.

## Requirements
- Gradle 7.5.1
- junit 4.13.2
- \>=Java 7.0 

### Using the program
- To run the program compile using gradle and the command. 
[-q to suppress all non error logs]
[--console plain to force gradle to compile a plain (no colour/rich output) console application]
```sh
./gradlew -q --console plain run
```
- To run the junit tests generated for this project use the command
```
./gradlew test
```

[Example Usecase](https://github.com/Valllls/AnagramChecker/blob/main/exampleResults.png?raw=true)
