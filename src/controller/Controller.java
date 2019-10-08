package controller;

import java.util.ArrayList;
import java.util.Scanner;

import bean.Student;
import services.StudentServiceImpl;
import services.StudentService;

public class Controller
{

	// Utility method Validates integer input
	public static int takeInt(Scanner sc)
	{
		int number;
		do
		{
			while ( !sc.hasNextInt() )
			{
				System.out.println("That's not a number!");
				sc.next();
			}
			number = sc.nextInt();
		}
		while ( number <= 0 );

		return number;
	}

	// Utility method Validates input in yyyy-mm-dd format
	public static String takeDate(Scanner sc)
	{
		String date = sc.nextLine();
		while ( !date.matches("\\d{4}-\\d{2}-\\d{2}") )
		{
			System.out.println("That's not in [ yyyy-mm-dd ] format");
			System.out.print("Enter date again : ");
			date = sc.nextLine();
		}

		return date;
	}

	// Main function
	public static void main(String[] args)
	{
		StudentService s = new StudentServiceImpl();
		int option = 1;
		Scanner input = new Scanner(System.in);
		while ( option != 0 )
		{

			System.out
					.println("Press :\n1 to add \n2 to Search \n3 to update using Reg No\n4 to show List\n5 to delete\n6 to find registration no\n0 to exit\n");
			option = takeInt(input);

			switch ( option )
			{
				case 1:
					Student student = new Student();
					System.out.print("Enter name : ");
					input.nextLine();
					student.setName(input.nextLine());
					System.out.print("Enter Age : ");
					student.setAge(takeInt(input));
					System.out.print("Enter DOB in yyyy-mm-dd format : ");
					input.nextLine();
					student.dob.setDOB(takeDate(input));
					System.out.print("Enter fathers name : ");
					student.parents.setFathersName(input.nextLine());
					System.out.print("Enter mothers name : ");
					student.parents.setMothersName(input.nextLine());
					System.out.print("Enter local guardian name : ");
					student.parents.setLocalGuardian(input.nextLine());
					System.out.print("Enter emergency contact  : ");
					student.parents.setEmergencyContactNo(input.nextLine());
					System.out.print("Enter Locality  : ");
					student.address.setLocality(input.nextLine());
					System.out.print("Enter city  : ");
					student.address.setCity(input.nextLine());
					System.out.print("Enter State  : ");
					student.address.setState(input.nextLine());
					System.out.print("Enter pinCode  : ");
					student.address.setPinCode(takeInt(input));
					s.addStudent(student);

					System.out.println("Your entry has been  added \n");
					break;
				case 2:
					int op1 = 1;
					System.out.println("Press\n1 : By Name\n2 : By Roll no\n");
					op1 = input.nextInt();
					switch ( op1 )
					{
						case 1:
							ArrayList<Student> students = new ArrayList<Student>();
							input.nextLine();
							System.out.print("Enter Name : ");
							students = s.getStudentByName(input.nextLine());
							if ( students != null )
								s.displayList(students);
							else
							{
								System.out.print("Name not found");
							}
							System.out.print("\n");
							break;
						case 2:
							System.out.print("Enter Roll No : ");
							student = s.getStudentByRoll(input.nextInt());
							if ( student != null )
								s.displayStudent(student);
							else
								System.out.print("Roll not found");
							System.out.print("\n");
							break;
						default:
							break;
					}
					break;
				case 3:
					System.out.print("Enter Admin Password : ");
					input.nextLine();
					String pass1 = input.nextLine();
					if ( pass1.equals("vaibhav") )
					{
						int op2 = 1;
						System.out.print("Enter registration number : ");
						int reg = input.nextInt();
						System.out
								.println("Press\n1 : update name/age/dob\n2 : update parents details\n3 :  update address\n");
						op2 = input.nextInt();
						switch ( op2 )
						{
							case 1:
								int op3 = 1;
								System.out
										.println("Press\n1 : update name\n2 :   update age\n3 :  update dob\n");
								op3 = input.nextInt();
								switch ( op3 )
								{
									case 1:
										System.out.print("Enter new Name : ");
										input.nextLine();
										try
										{
											s.updateName(reg, input.nextLine());
										}
										catch ( Exception e )
										{
											System.out
													.println("Name could not be updated");
										}
										break;
									case 2:
										System.out.print("Enter new Age : ");
										try
										{
											s.updateAge(reg, input.nextInt());
										}
										catch ( Exception e )
										{
											System.out
													.println("Age could not be updated");
										}
										break;
									case 3:
										System.out
												.print("Enter new Dob as yyyy-mm-dd  : ");
										input.nextLine();
										try
										{
											s.updateDob(reg, input.nextLine());
										}
										catch ( Exception e )
										{
											System.out
													.println("Dob could not be updated");
										}
										break;
									default:
										break;
								}
								break;
							case 2:
								int op4 = 1;
								System.out
										.println("Press\n1 : update Father's Name\n2 : update Mother's Name\n3 :  update Local Guardian\n4 :  update emergency Contact\n");
								op4 = input.nextInt();
								switch ( op4 )
								{
									case 1:
										System.out
												.print("Enter new Father's Name : ");
										input.nextLine();
										try
										{
											s.updateFathersName(reg,
													input.nextLine());
										}
										catch ( Exception e )
										{
											System.out
													.println("Father's Name could not be updated");
										}
										break;
									case 2:
										System.out
												.print("Enter new Mother's Name : ");
										input.nextLine();
										try
										{
											s.updateMothersName(reg,
													input.nextLine());
										}
										catch ( Exception e )
										{
											System.out
													.println("Mother's Name could not be updated");
										}
										break;
									case 3:
										System.out
												.print("Enter new Local Guardian : ");
										input.nextLine();
										try
										{
											s.updateLocalGuardian(reg,
													input.nextLine());
										}
										catch ( Exception e )
										{
											System.out
													.println("Local Guardian Name could not be updated");
										}
										break;
									case 4:
										System.out
												.print("Enter new emergency Contact  : ");
										input.nextLine();
										try
										{
											s.updateEmergencyContact(reg,
													input.nextLine());
										}
										catch ( Exception e )
										{
											System.out
													.println("Emergency Contact could not be updated");
										}
										break;
									default:
										break;
								}
								break;
							case 3:
								int op5 = 1;
								System.out
										.println("Press\n1 : update Locality\n2 : update City\n3 :  update State\n");
								op5 = input.nextInt();
								switch ( op5 )
								{
									case 1:
										System.out
												.print("Enter new Locality : ");
										input.nextLine();
										try
										{
											s.updateLocality(reg,
													input.nextLine());
										}
										catch ( Exception e )
										{
											System.out
													.println("Locality could not be updated");
										}
										break;
									case 2:
										System.out.print("Enter new City : ");
										input.nextLine();
										try
										{
											s.updateCity(reg, input.nextLine());
										}
										catch ( Exception e )
										{
											System.out
													.println("City could not be updated");
										}
										break;
									case 3:
										System.out.print("Enter new State : ");
										input.nextLine();
										try
										{
											s.updateState(reg, input.nextLine());
										}
										catch ( Exception e )
										{
											System.out
													.println("State could not be updated");
										}
										break;
									default:
										break;
								}
						}
					}
					else
						System.out.println("Wrong password cannot update");
					break;
				case 4:
					System.out
							.print("press\n1 for Total details\n2 for only names\n");
					int op = input.nextInt();
					switch ( op )
					{
						case 1:
							try
							{
								s.displayAll();
							}
							catch ( Exception e )
							{
								System.out
										.println("Error : Data could not be dispalyed impl");
							}
							System.out.println();
							break;
						case 2:
							try
							{
								s.DisplayAllNames();
							}
							catch ( Exception e )
							{
								System.out
										.println("Error : Data could not be dispalyed impl");
							}
							System.out.println();
							break;
					}
					break;
				case 5:
					System.out.print("Provide Roll no : ");
					try
					{
						if ( s.deleteStudent(input.nextInt()) )
							System.out.println("Entry deleted");
						else
							System.out.println("deletion failed");
					}
					catch ( Exception e )
					{
						System.out.println("Error in deletion");
					}
					break;
				case 6:
					System.out.print("Enter Password : ");
					input.nextLine();
					String pass2 = input.nextLine();
					if ( pass2.equals("vaibhav") )
					{
						System.out.print("Enter a Roll number : ");
						int r = input.nextInt();
						try
						{
							System.out
									.println("The registration no mapped to Roll is : "
											+ s.checkRegNo(r));
						}
						catch ( Exception e )
						{
							System.out
									.println("Error : Resgistration could not be mapped impl");
						}
					}
					else
					{
						System.out.println("Wrong password\n");
					}
					break;
				case 0:
					option = 0;
					input.close();
					break;

			}
		}
	}
}
