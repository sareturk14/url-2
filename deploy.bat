@echo off
echo ============================================
echo  URL Kisaltma - Build ve Deploy Script
echo ============================================
echo.

REM Maven'i IntelliJ'in embed ettigi surumden bul
set MAVEN_PATH=
for /d %%d in ("%USERPROFILE%\.m2" "C:\Program Files\JetBrains\*" "C:\Program Files\IntelliJ*") do (
    if exist "%%d" echo Kontrol: %%d
)

REM Cevresel degisken MAVEN_HOME varsa kullan
if defined MAVEN_HOME (
    echo Maven bulundu: %MAVEN_HOME%
    "%MAVEN_HOME%\bin\mvn.cmd" clean package -DskipTests
    goto :docker
)

echo Maven PATH'de bulunamadi.
echo.
echo Lutfen once IntelliJ IDEA uzerinden projeyi build edin:
echo   1. Maven sekmesi -^> bm470 -^> Lifecycle -^> package
echo   veya Ctrl+F9 ile Build Project
echo   (target\bm470.war olusacak)
echo.
echo Ardindan bu scripti tekrar calistirin veya asagidaki Docker komutlarini calistirin:
echo.
echo   docker compose down -v
echo   docker compose up --build
echo.
pause
exit /b 1

:docker
echo.
echo Build basarili! Docker baslatiliyor...
echo.
docker compose down -v
docker compose up
pause
