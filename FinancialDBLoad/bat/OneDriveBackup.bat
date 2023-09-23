@echo off
REM OneDriveBackup for FinancialDBLoad data files

if not exist "%userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\FinancialDBLoad\" mkdir %userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\FinancialDBLoad

xcopy data\*.xml %userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\FinancialDBLoad\data /I /Y
