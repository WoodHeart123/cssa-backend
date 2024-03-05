import requests
from bs4 import BeautifulSoup
import pymysql

db = pymysql.connect(
    host="",
    port=0,
    user= '',
    password='',
    database=''
)
cursor = db.cursor()
    
def insertDB(departmentID, courseName, credit, courseNum, departmentAbrev):
    try:
        sql = '''insert into course (departmentID, courseName, credit, courseNum, departmentAbrev) values ("%s","%s","%s",%s,"%s") ON DUPLICATE KEY UPDATE credit = "%s"''' % (departmentID, courseName, credit, courseNum, departmentAbrev, credit)
        cursor.execute(sql)
    except Exception as e:
        print(e)

if __name__ == "__main__":

    cursor.execute("SELECT departmentID, departmentAbrev FROM department")
    departmentList = cursor.fetchall()
    url = "https://guide.wisc.edu/courses/"
    for (departmentID,departmentAbrev) in departmentList:
        try:
            file = requests.get(url + departmentAbrev.replace(" ", "_").lower())
            html = BeautifulSoup(file.text,'html.parser')
            for courseblock in html.find_all("div", class_="courseblock"):
                name = courseblock.find("strong")
                courseText = name.text.split("—",1)
                try:  
                    lastOpenYear = int(courseblock.find_all("span", class_="cbextra-data")[-1].text[-4:])
                    courseNum = int(courseText[0].strip()[-3:])
                except:
                    print(name.text + " skipped")
                    continue
                if courseNum < 800 and lastOpenYear >= 2021:
	                courseName = courseText[1].strip().replace("\"","'").lower().title()
	                credit = courseblock.find("p", class_="courseblockcredits").text.replace(".","").replace("credits","").replace("credit","").strip()
	                insertDB(departmentID, courseName, credit,courseNum,departmentAbrev)
            print(str(departmentID) + " finished")
        except Exception as e:
            print(e)
    db.commit()
