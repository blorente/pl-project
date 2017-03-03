#!/bin/bash

javac -d ../out -sourcepath ../src ../src/prueba/Prueba.java
java -cp ../out prueba.Prueba
