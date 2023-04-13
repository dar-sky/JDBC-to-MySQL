# JDBC-to-MySQL

1) load the driver:
 Class.forName("com.mysql.jdbc.Driver")//

2)Create a Connection:
 Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dbname","root","root");


3)Create query, Statement , PreparedStatement , CallableStatement
eg 
 String q="select * from students";

 Statement stmt=con.createStatement();

 ResultSet set=stmt.executeQuery(q);

4)Process the data :
 
 while(set.next())
 {
   int id=set.getInt("studentID");
   String name=set.getString("studentName");
   System.out.println(id);
   System.out.println(name);

 }

5) Close the connection:
 con.close();
