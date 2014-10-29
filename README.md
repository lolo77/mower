Mower
=====

Mower from MowItNow


Requirements
------------
This implementation of Mower is designed in an evolutive way.
The requirements do not specify what should happen if multiple mowers enters in collision, so I decided to prevent a  mower from moving if a collision is detected on the next movement.

Architecture
------------
I made layers to separate the engine from the data transfers (ie. read and write operations). To make the maintenance easy, the project is divided in 4 main parts :
* App : The entry point
* Core : The engine and the working entities
* Transfer : The in/out layer

Dependencies
------------
* JDK 1.7
* Maven 3
* SLF4J / LOG4J
* JUnit

Compile, Run and Test
---------------------
    mvn clean package
    cd target
    java -jar mower-<version>.jar ..\testSuccess.txt
    
