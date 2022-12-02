package com.dynamicInput.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.jdbcUtil.JdbcConnection;


public class CrudApp {
	

	public static void main(String[] args) {
		
		Integer id;
		String name;
		Integer age;
		String address;
		Scanner scan = new Scanner(System.in);
		
		try(Connection connection = JdbcConnection.getJdbcConnection()) {
			
			if(connection != null) {
				
				int number;
				
				do {
					System.out.println("\n---------- Menu for CRUD Operations ------------- ");
					System.out.println("  1. Insert the data");
					System.out.println("  2. Update the data");
					System.out.println("  3. Delete the data");
					System.out.println("  4. view all the data");
					System.out.println("  5. Exit");
					System.out.println("---------------------------------------------------");
					System.out.println("Choose one operation to perform : ");
					
					number = scan.nextInt();
					
					String selectQuery= "select count(*) from student where sid=?";
					PreparedStatement selectPstmt = connection.prepareStatement(selectQuery);
					
					switch(number) {
					
					case 1 : {
						
						System.out.print("Enter name :");
						name = scan.next();
						
						System.out.print("Enter age :");
						age = scan.nextInt();
						
						System.out.print("Enter address :");
						address = scan.next();
						
						String insertSqlQuery = "insert into student(`sname`,`sage`,`saddress`) values(?,?,?)";
						
						try(PreparedStatement Insertpstmt = connection.prepareStatement(insertSqlQuery)){
							if(Insertpstmt != null) {
								Insertpstmt.setString(1, name);
								Insertpstmt.setInt(2, age);
								Insertpstmt.setString(3, address);
								
								int rows = Insertpstmt.executeUpdate();
								System.out.println("No. of rows added - "+rows);
							}
							System.out.println("Data Inserted successfully....");
						
					}catch(SQLException se) {
						se.printStackTrace();
					}
						break;
					}
					case 2 : {
						
						System.out.println("Enter the id of student to update :");
						id=scan.nextInt();
						
						if(selectPstmt != null) 
							selectPstmt.setInt(1, id);
						
						try(ResultSet resultSet = selectPstmt.executeQuery()){
							int count=0;
							if(resultSet.next()) {
								count = resultSet.getInt(1);
							}
							
							if(count  == 1) {
								System.out.println("Record found. Please update the record  :");
								System.out.println("Enter the name : ");
								name=scan.next();
								System.out.println("Enter age : ");
								age=scan.nextInt();
								System.out.println("Enter address : ");
								address=scan.next();
								String UpdateSqlQuery = "update student set sname=?,sage=?,saddress=? where sid="+id+"";
								try(PreparedStatement Updatepstmt = connection.prepareStatement(UpdateSqlQuery)){
									if(Updatepstmt != null) {
										Updatepstmt.setString(1, name);
										Updatepstmt.setInt(2, age);
										Updatepstmt.setString(3, address);
										int rows = Updatepstmt.executeUpdate();
										System.out.println("No. of rows updated - "+rows);
									}
									System.out.println("Data Updated successfully.......");
								}catch (SQLException se) {
									se.printStackTrace();
								}
							}
							else
								System.out.println("Record not found.....");
						}catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
					
					case 3 :{
						
						System.out.println("Enter the id of student to delete :");
						id=scan.nextInt();
						
						String deleteSqlQuery = "delete from student where sid=?";
						try(PreparedStatement Deletepstmt = connection.prepareStatement(deleteSqlQuery)){
							if(Deletepstmt != null) {
								Deletepstmt.setInt(1, id);
								
								int rows = Deletepstmt.executeUpdate();
								System.out.println("No. of rows deleted - "+rows);
							}
							System.out.println("Data Deleted successfully.........");
						}catch(SQLException se) {
							se.printStackTrace();
						}
						break;
					}
					
					case 4 : {
						String selectSqlQuery = "select sid,sname,sage,saddress from student";
						try(PreparedStatement SelectStmt = connection.prepareStatement(selectSqlQuery)){
							if(SelectStmt != null) {
								try(ResultSet resultSet=SelectStmt.executeQuery()){
									if(resultSet != null) {
										System.out.println("Id \tName \tAge \tAddress");
										System.out.println("-------------------------------");
										while (resultSet.next()) {
											System.out.println(resultSet.getInt(1)+"\t"+resultSet.getString(2)+"\t"+resultSet.getInt(3)
											+"\t"+resultSet.getString(4));
										}
									}
								}catch(SQLException se) {
									se.printStackTrace();
								}
							}
						}
						
						break;
					}
					
					case 5:{
						System.out.println("Thanks for using........");
						System.exit(0);
						
					}
						
					default:{
						System.out.println("\n Enter the number from 1 to 5.");
						break;
					}
					
					}
					
				} while (number>0);
				
			}
			
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		
		
	}

}
