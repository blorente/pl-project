#!/bin/bash
rm -r ../out
mkdir ../out
javac -d ../out -sourcepath ../src ../src/prueba/Prueba.java
java -cp ../out prueba.Prueba
