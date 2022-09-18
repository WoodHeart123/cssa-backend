import requests
from bs4 import BeautifulSoup
import pymysql

def insertDB(department, departmentAbrev):
    # 插入数据
    try:
        sql = "insert into department (department, departmentAbrev) values ('%s','%s')" % (department, departmentAbrev)
        cursor.execute(sql)
        db.commit()
    except Exception as e:
        db.rollback()
        print(e)


db = pymysql.connect(
    host="sh-cynosdbmysql-grp-5ljk41ng.sql.tencentcdb.com",
    port=28513,
    user='root',
    password='NST4VU3f',
    database='springboot_demo'
)

cursor = db.cursor()


url = "https://guide.wisc.edu/courses/"
strhtml = requests.get(url)
soup = BeautifulSoup(strhtml.text, 'lxml')
count = 0
for a in soup.find_all(id='atozindex'):
    for b in a.find_all('a'):
        de_text = b.text
        de_text = de_text.replace(")", "")
        info = de_text.split("(")
        if len(info) > 2:
            department = info[0].strip() + f"({info[1].strip()})"
            departmentAbrev = info[2].strip()
            insertDB(department, departmentAbrev)
        else:
            department = info[0].strip()
            departmentAbrev = info[1].strip()
            insertDB(department, departmentAbrev)