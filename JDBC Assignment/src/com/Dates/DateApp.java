package com.Dates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.jdbcUtil.JdbcConnection;

public class DateApp {

	public static void main(String[] args) {
		
		String name;
		String address;
		String DOB;
		String DOJ;
		String DOM;
		
		Scanner scan = new Scanner(System.in);
		
		try(Connection connection = JdbcConnection.getJdbcConnection()){
			
			if(connection != null) {
				
				int number;
				
				do {
					
					System.out.println("\n---------- Menu for CRUD Operations ------------- ");
					System.out.println("  1. Insert operation");
					System.out.println("  2. Retrieval operation");
					System.out.println("  3. Exit");
					System.out.println("---------------------------------------------------");
					System.out.print("Choose one operation to perform : ");
					
					number = scan.nextInt();
					
					switch(number) {
					
					case 1 : {
						
						System.out.print("Enter name :");
						name = scan.next();
						System.out.print("Enter address :");
						address = scan.next();
						System.out.print("Enter DOB(dd-MM-yyyy) :");
						DOB = scan.next();
						System.out.print("Enter DOJ(MM-dd-yyyy) :");
						DOJ = scan.next();
						System.out.print("Enter DOM(yyyy-MM-dd) :");
						DOM = scan.next();
						
						// Date Converting from String to Date
						
						SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
						SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy");
						java.util.Date uDOB = sdf1.parse(DOB);
						java.util.Date uDOJ = sdf2.parse(DOJ);
						
						long tDOB = uDOB.getTime();
						long tDOJ = uDOJ.getTime();
						
						java.sql.Date sqlDOB = new java.sql.Date(tDOB);
						java.sql.Date sqlDOJ = new java.sql.Date(tDOJ);
						// if it is yyyy-MM-dd then we can use valueOf() method from string to sql.Date 
						java.sql.Date sqlDOM = java.sql.Date.valueOf(DOM);
						
						String insertQuery = "insert into userdata(`name`,`address`,`DOB`,`DOJ`,`DOM`) values (?,?,?,?,?)";
						
						try(PreparedStatement Insertpstmt = connection.prepareStatement(insertQuery)){
							if(Insertpstmt != null) {
								Insertpstmt.setString(1, name);
								Insertpstmt.setString(2, address);
								Insertpstmt.setDate(3, sqlDOB);
								Insertpstmt.setDate(4, sqlDOJ);
								Insertpstmt.setDate(5, sqlDOM);
								
								int rows = Insertpstmt.executeUpdate();
								System.out.println("No. of rows updated - "+rows);
							}
						}catch (SQLException e) {
							System.out.println("Exception occur in prepare");
						}
						
						break;
					}
					
					case 2 : {
						
						System.out.print("Enter name for retrieval :");
						name = scan.next();
						String selectQuery = "select name,address,DOB,DOJ,DOM from userdata where name=?";
						
						try(PreparedStatement Selectpstmt = connection.prepareStatement(selectQuery)){
							
							if(Selectpstmt != null) {
								Selectpstmt.setString(1, name);
								
								try(ResultSet resultSet = Selectpstmt.executeQuery();){
									if(resultSet != null) {
										
										if(resultSet.next()) {
											System.out.println("Name \tAddress   DOB       DOJ       DOM");
											System.out.println("---------------------------------------------");
											String uname = resultSet.getString(1);
											String uaddress = resultSet.getString(2);
											
											// Converting Date to String for our format 
											SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
											java.util.Date uDOB = resultSet.getDate(3);
											java.util.Date uDOJ = resultSet.getDate(4);
											java.util.Date uDOM = resultSet.getDate(5);
											
											String dob = sdf.format(uDOB);
											String doj = sdf.format(uDOJ);
											String dom = sdf.format(uDOM);
											
											System.out.println(uname+"\t"+uaddress+" "+dob+" "+doj+" "+dom);
											
										}else {
											System.out.println("Record not available for the given name: " + name);
										}
									}
								}catch (SQLException e) {
									e.printStackTrace();
								}
							}
							
						}catch (SQLException e) {
							e.printStackTrace();
						}
						
						break;
					}
					
					case 3 : {
						System.out.println("Thanks for using........");
						System.exit(0);
					}
					
					default : {
						System.out.println("\n Enter the number from 1 to 5.");
						break;
					}
					
					}
					
				}while(number>0);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
