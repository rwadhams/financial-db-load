README
======

ConvertCSV2XMLApp
-----------------
Copy Suncorp downloaded data files to the input location. Edit the files, removing header and trailer lines.

Run App.

Input:	C:\Mongo\Financial_DB_CSV_Data\*.csv
Output:	C:\Mongo\Financial_DB_CSV_Data\*.xml




DataValueReportingApp
---------------------
Run anytime to report on commonly used data vales from the database.

Output:	out/data-value-report.txt



FinancialDBLoadApp
------------------
Run App after all data files have been prepared. Location: C:\Mongo\Financial_DB_XML_Data\*.txt

Input:	Run parameter
			REFRESH		//Direct updates to database
			DML			//Data Manipulation Language (Insert statements)

Output:	Console output. Search for ZZZ, which reports unknown or invalid values.

