import pandas as pd
import mysql.connector

# Read the Excel file
file_name = "Madison_Restuarants.xlsx"
df = pd.read_excel(file_name)

# Extract the required columns
extracted_data = df[["Name", "Address", "Coordinate"]]

# Database connection settings
db_config = {
    "host": "",
    "port": 0,
    "user": "",
    "password": "",
    "database": "",
}

# Connect to the MySQL database
connection = mysql.connector.connect(**db_config)
cursor = connection.cursor()


# Insert the extracted data into the table
for index, row in extracted_data.iterrows():
    name = row["Name"]
    address = row["Address"]
    coordinate_str = row["Coordinate"]
    # Assuming the coordinate string is in the format "x,y"
    x, y = map(float, coordinate_str.split(','))
    insert_query = f"""
    INSERT INTO restaurant (name, location, geoLocation)
    VALUES (%s, %s, ST_PointFromText('POINT({x} {y})'))
    """

    cursor.execute(insert_query, (name, address))
    connection.commit()

# Close the database connection
cursor.close()
connection.close()
