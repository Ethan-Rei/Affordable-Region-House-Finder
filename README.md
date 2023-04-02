# Affordable-Region-House-Finder
# Required libraries:
- Apache Commons Lang
- Apache Commons Math
- JFreeChart
- MySQL Connector
- OpenCSV (only for ImportDB)
- Weka (also timeseriesForecasting jar, core.jar, mtj.jar, arpack_combined.jar in weka.jar)

# How to setup
Install MySQL (or preferred database) and create a schema called "nhpi" or whatever schema name you want.\
Clone the repository.

If you are using your own IDE, configure the buildpath appropriately.

If you are using Eclipse:\
Make a new Java project called "Affordable-Region-House-Finder" in the same folder you cloned in.\
Import all the libraries required in Java Build Path.

Once all setup, you must import all the data from your CSV into your database (see note if not MySQL).

If you used MySQL you can just run the ImportDB.java at least once to import all the data in the CSV to your MySQL database.\
**Change the ip, port, and schema in the class if it is not the default values.**\
Input your username and password and it should automatically build the database.

You may proceed to run Application.java to use the software.

### Note
If you use a database other than MySQL, make sure to create 1 table called "data" with 4 columns:
- refdate (of type Date, Month-Year in the CSV)
- location_name (of type char, Provinces, etc. in the CSV)
- location_level (of type char, Region, City, Country in the CSV)
- property_value (of type decimal, the NHPI values in the CSV)
