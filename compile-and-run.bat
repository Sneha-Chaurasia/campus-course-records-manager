@echo off
echo ========================================
echo Campus Course & Records Manager (CCRM)
echo ========================================
echo.

echo Compiling Java source files...
javac -d . @sources.txt
if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b %errorlevel%
)
echo Compilation successful.
echo.

echo Running application...
java -cp . edu.ccrm.CCRMApplication

echo.
echo Application finished.
pause

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Running CCRM Application with assertions enabled...
echo.

java -ea -cp . edu.ccrm.CCRMApplication

echo.
echo Application terminated.
pause
