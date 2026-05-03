@echo off
echo Proje build ediliyor...
echo Bu islemi IntelliJ IDEA icinde yapmaniz gerekiyor:
echo 1. Build -^> Build Project (Ctrl+F9)
echo 2. Maven sekmesinden package calistirin
echo 3. Target klasorunde bm470.war olusacak
echo.
echo Simdilik Docker ile test edelim...
docker run -d --name tomcat-test -p 8080:8080 tomcat:10.1-jdk21
echo Tomcat baslatildi: http://localhost:8080
pause
