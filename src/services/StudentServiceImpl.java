package services;
import java.util.ArrayList;
import dao.StudentDao;
import dao.StudentDaoInterface;
import bean.Student;


public class StudentServiceImpl implements StudentService
{
	StudentDaoInterface i = new StudentDao();
	//Adds a new student to the data base sorted by roll number;
	public boolean addStudent(Student student)
	{
		boolean res = false;
		
			try 
			{
				res = i.addStudent(student);
				return res;
			} 
			catch (Exception e)
			{
				return res;
			}
	}
	
	//returns all students having a given name
	public ArrayList<Student> getStudentByName(String name)
	{
		ArrayList<Student> list = new ArrayList<Student>();
		try
		{
			list = i.getStudentByName(name);
		}
		catch(Exception e)
		{
			System.out.println("Student name could not be found in impl\n");
		}
		return list;
	}
	
	
	//returns Student object in the ArrayList with given roll
	public Student getStudentByRoll(int roll)
	{
		Student s = null;
		try 
		{
			 s = i.getStudent(roll);
		} 
		catch (Exception e) 
		{
			System.out.println("Student with Roll"+roll+" not found error in impl\n");
		}
		return s;	
	}
	
	
	//deletes a student
	public boolean deleteStudent(int roll)
	{
		StudentDao d = new StudentDao();
		try
		{
			return d.deleteStudent(roll);
		} 
		catch (Exception e)
		{
			System.out.println("ERROR : Student could not be deleted\n");
			return false;
		}
		
	}
	
	//Prints all student details in the database;
	public void displayAll()
	{
		ArrayList<Student> list = new ArrayList<Student>();
		try 
		{
			list = i.allStudentsDetails();
		} 
		catch (Exception e) 
		{
			System.out.println("Could not fetch student details impl\n");
		}
		
		if(list.isEmpty())
		{
			System.out.println("empty");
			return ;
		}
			System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			 
			System.out.printf("%15s %20s %15s %20s %25s %25s %25s %25s %20s %25s %20s", "ROLL","NAME", "AGE", "DOB", "FATHER","MOTHER","GUARDIAN","LOCALITY","CITY","STATE","PINCODE");
			System.out.println();
			System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			for(Student s : list)
			{
				System.out.printf("%15d %20s %15d %20s %25s %25s %25s %25s %20s %25s %20d",s.getRollNo(),s.getName(), s.getAge(), s.dob.getDOB(),s.parents.getFathersName(),s.parents.getMothersName(),s.parents.getLocalGuardian(), s.address.getLocality(),s.address.getCity(),s.address.getState(),s.address.getPinCode());
				System.out.println();
			}
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			
	}	
	
	//Prints only names of all students present in the database
	public void DisplayAllNames()
	{
		ArrayList<String> list = new ArrayList<String>();
		try 
		{
			list = i.allStudentsName();
		} 
		catch (Exception e) 
		{
			System.out.println("Error : Names could not be fetched impl\n");
		}
		if(list.isEmpty())
			return;
		int len = list.size();
		System.out.println("----------------------------------------------------------");
		 
		System.out.printf("%20s", "NAMES");
		System.out.println();
		System.out.println("-----------------------------------------------------------");
		for(int i=0;i<len;i++)
		{
				System.out.printf("%20s",list.get(i));
				System.out.println();	
		}
		System.out.println("------------------------------------------------------------");
	}
	
	//Utility method for DisplayAllNames()
	public void displayList(ArrayList<Student> list )
	{
		System.out.println("------------------------------------------------------------------");
		 
		 System.out.printf("%20s %10s %10s", "NAME","ROLL", "AGE");
		 System.out.println();
		 System.out.println("------------------------------------------------------------------");

			
			 for(Student s : list)
			 {
				System.out.printf("%20s %10s %10s",s.getName(),s.getRollNo(), s.getAge());
				System.out.println();
			}
			System.out.println("--------------------------------------------------------------------");
			

	}
	
	//Prints  all details of a student to console
	public void displayStudent(Student s) {
		// TODO Auto-generated method stub
		
	}
	
	
	//Updation methods for all fields of student database
	public boolean  updateName(int reg,String name) 
	{
		try 
		{
			return i.updateName(reg, name);		
		} 
		catch (Exception e)
		{
			System.out.println("Name may not be updated impl\n");
			return false;
		}
	}
	public boolean  updateAge(int reg,int age)
	{
		try 
		{
			return (i.updateAge(reg, age));
		} 
		catch (Exception e) 
		{
			System.out.println("Age may not be updated impl\n");
			return false;
		}
	}
	public boolean  updateRoll(int reg,int roll)
	{
		try 
		{
			return (i.updateRoll(reg, roll));
		} 
		catch (Exception e) 
		{
			System.out.println("Roll may not be updated impl\n");
			return false;
		}
	}
	public boolean  updateDob(int reg,String dob)
	{
		try 
		{
			return (i.updateDob(reg, dob));
		} 
		catch (Exception e) 
		{
			System.out.println("Dob may not be updated impl\n");
			return false;
		}
	}
	public boolean  updateFathersName(int reg,String fathersName)
	{
		try 
		{
			return (i.updateFathersName(reg, fathersName));
		} 
		catch (Exception e) 
		{
			System.out.println("Father's Name may not be updated impl\n");
			return false;
		}
	}
	public boolean  updateMothersName(int reg,String mothersName) 
	{
		try 
		{
			return (i.updateMothersName(reg, mothersName));
		} 
		catch (Exception e) 
		{
			System.out.println("Mother's Name may not be updated impl\n");
			return false;
		}
	}
	public boolean  updateLocalGuardian(int reg,String localGuardian) 
	{
		try 
		{
			return (i.updateLocalGuardian(reg, localGuardian));
		} 
		catch (Exception e) 
		{
			System.out.println("LocalGuardian Name may not be updated impl\n");
			return false;
		}
	}
	public boolean  updateEmergencyContact(int reg,String emergencyContact)
	{
		try 
		{
			return (i.updateEmergencyContact(reg, emergencyContact));
		} 
		catch (Exception e) 
		{
			System.out.println("Emergency Contact  may not be updated impl\n");
			return false;
		}
	}
	public boolean  updateLocality(int reg,String locality)
	{
		try 
		{
			return (i.updateLocality(reg, locality));
		} 
		catch (Exception e) 
		{
			System.out.println("Locality may not be updated impl\n");
			return false;
		}
	}
	public boolean  updateCity(int reg,String city)
	{
		try 
		{
			return (i.updateCity(reg, city));
		} 
		catch (Exception e) 
		{
			System.out.println("City may not be updated impl\n");
			return false;
		}
	}
	public boolean  updateState(int reg,String state)
	{
		try 
		{
			return (i.updateState(reg, state));
		} 
		catch (Exception e) 
		{
			System.out.println("State may not be updated impl\n");
			return false;
		}
	}
	
	
	//Returns registration number mapped to the provided roll number
	public int checkRegNo(int roll) 
	{
		try 
		{
			return i.checkRegNo(roll);
		} 
		catch (Exception e)
		{
			System.out.println("Exception occured while mapping registration number \n");
			return -1;
		}
	}
}





