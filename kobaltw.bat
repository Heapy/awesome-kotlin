@echo off
set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
java -jar "%DIRNAME%/kobalt/wrapper/kobalt-wrapper.jar" %*
