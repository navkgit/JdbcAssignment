package com.crud;

import java.sql.*;

import java.util.Scanner;

public class CrudOperation {

	public static void main(String[] args) throws SQLException {
		
		Integer id;
		String name;
		Integer age;
		String address;
		Scanner scan = new Scanner(System.in);
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		
		try {
			
			String url = "jdbc:mysql://localhost:3306/ineuron";
			String user = "root";
			String password = "root";
			
			connection = DriverManager.getConnection(url,user,password);
			
			Integer number;
			
			do {
				System.out.println("\n---------- Menu for CRUD Operations ------------- ");
				System.out.println("  1. Insert the data");
				System.out.println("  2. Update the data");
				System.out.println("  3. Delete the data");
				System.out.println("  4. Read the data");
				System.out.println("  5. Exit");
				System.out.println("---------------------------------------------------");
				System.out.println("Choose one operation to perform : ");
				
				number=scan.nextInt();
				
				switch (number) {
				
				// 1. Inserting Data
				case 1: {
					if(connection!=null) {
						
						statement = connection.createStatement();
						
						if(statement!=null) {
							
							System.out.println("Enter the name : ");
							name=scan.next();
							System.out.println("Enter age : ");
							age=scan.nextInt();
							System.out.println("Enter address : ");
							address=scan.next();
							
					String insertSqlQuery = String.format(
							"insert into student(`sname`,`sage`,`saddress`) values ('%s',%d ,'%s')",name,age,address);
					
					System.out.println(insertSqlQuery);
							
							int no_Of_Rows =statement.executeUpdate(insertSqlQuery);
							
							System.out.println("No. of rows addedd : "+no_Of_Rows);
							System.out.println("\nData Inserted Successfully..........");
						}
					}
					break;
				}
				
				// 2. Updating data
				
				case 2: {
					if(connection!=null) {
						
						statement = connection.createStatement();
						
						if(statement!=null) {
							
							System.out.println("Enter the id of student to update :");
							id=scan.nextInt();
							
							System.out.println("Enter the name : ");
							name=scan.next();
							System.out.println("Enter age : ");
							age=scan.nextInt();
							System.out.println("Enter address : ");
							address=scan.next();
							
					String UpdateSqlQuery = String.format(
							"update student set sname='%s',sage=%d,saddress='%s' where sid=%d",name,age,address,id);
					
					System.out.println(UpdateSqlQuery);
							
							int no_Of_Rows =statement.executeUpdate(UpdateSqlQuery);
							
							System.out.println("No. of rows addedd : "+no_Of_Rows);
							System.out.println("\nData Updated Successfully.........");
						}
					}
					break;
				}
				
				// 3. Delete the data
				
				case 3: {
					if(connection!=null) {
						
						statement = connection.createStatement();
						
						if(statement!=null) {
							
							System.out.println("Enter the id of student to delete :");
							id=scan.nextInt();
							
							String DeleteSqlQuery =String.format("delete from student where sid=%d", id);
							int no_Of_Rows =statement.executeUpdate(DeleteSqlQuery);
							
							System.out.println("No: of rows affected - "+no_Of_Rows);
							System.out.println("\nId : "+id+" is deleted from database.....");
						}
					}
					
					break;
				}
				
				// 4. Reading all the data from the database
				case 4: {
					if(connection!=null) {
						 statement = connection.createStatement();
						 
						 if(statement != null) {
							 String SelectSqlQuery = "select sid,sname,sage,saddress from student";
							 resultset  = statement.executeQuery(SelectSqlQuery);
							 
							 if(resultset != null) {
								 System.out.println("ID \tName \tAge \tAddress");
								 while(resultset.next()) {
									 id=resultset.getInt(1);
									 name=resultset.getString(2);
									 age=resultset.getInt(3);
									 address=resultset.getString(4);
									 System.out.println(id+"\t"+name+"\t"+age+"\t"+address);
								 }
							 }
						 }
					}
					break;
				}
				case 5:
					System.out.println("Thanks for using........");
					System.exit(0);
					
				default:
					System.out.println("\n Enter the number from 1 to 5.");
					break;
				}
				
			}while(number>0);
			
		}
		
		catch(SQLException se) {
			se.printStackTrace();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			if(resultset != null)
				resultset.close();
			if(statement != null)
				statement.close();
			if(connection != null)
				connection.close();
			if(scan != null)
				scan.close();
		}
		
	}

}
