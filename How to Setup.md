## Setting Up IDE: Apache NetBeans and Database Server: WampServer with PhpMyAdmin (MySQL)

### In PhpMyAdmin:

1. **Run WampServer and Access PhpMyAdmin:**
   - Start WampServer.
   - Access PhpMyAdmin from WampServer.

2. **Login to PhpMyAdmin:**
   - Use Username `"root"` and leave the Password field empty.
   - Make sure Server choice is set to `"MySQL"`.

3. **Create Database:**
   - Create a new database named `"se_db"` in PhpMyAdmin.

4. **Import Database File:**
   - Click on the `"Import"` tab in PhpMyAdmin.
   - Choose the file named `"se_db.sql"` from the `"DB"` folder.
   - Execute the import to populate the `"se_db"` database.

### In NetBeans:

1. **Open the Java File:**
   - Open the Java file from NetBeans.

2. **Go to Project Properties:**
   - Go to Project Properties and navigate to `"Libraries"`.

3. **Add JAR/Folder:**
   - Click on `"Add JAR/Folder"`.
   - Choose `"mysql-connector-java-8.0.15"` in the `"DB"` folder.

### To Open the Built JAR File:

1. **Navigate to Dist Folder:**
   - Open `"Electro_Bill"` and go to `"dist"`.

2. **Run the JAR File:**
   - Click on `"Electro_Bill.jar"` and run the file.
