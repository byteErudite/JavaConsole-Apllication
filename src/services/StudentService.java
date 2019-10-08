package services;
import java.util.ArrayList;

import bean.Student;

public interface StudentService 
{
	//Add a new student to the database;
		public boolean addStudent(Student student);
			
		//returns Student object in the ArrayList with given name
		public ArrayList<Student> getStudentByName(String name);
		
		//returns Student object in the ArrayList with given roll
		public Student getStudentByRoll(int roll);
		
		//Updates student s details and returns updated ArrayList
		public boolean  updateName(int reg,String name) throws Exception;
		public boolean  updateAge(int reg,int age) throws Exception;
		public boolean  updateRoll(int reg,int roll)throws Exception;;
		public boolean  updateDob(int reg,String name) throws Exception;
		public boolean  updateFathersName(int reg,String name) throws Exception;
		public boolean  updateMothersName(int reg,String name) throws Exception;
		public boolean  updateLocalGuardian(int reg,String name) throws Exception;
		public boolean  updateEmergencyContact(int reg,String name) throws Exception;
		public boolean  updateLocality(int reg,String name) throws Exception;
		public boolean  updateCity(int reg,String name) throws Exception;
		public boolean  updateState(int reg,String name) throws Exception;
		
		public int checkRegNo(int roll) throws Exception;
		
		//Updates student s details and returns updated ArrayList
		//public Student updateDetailsByRoll(int roll);
		
		//deletes a student
		public boolean deleteStudent(int roll) throws Exception;
		
		
		//Prints all student details in the database;
		public void displayAll() throws Exception;
		
		//Displays details of the given student object;
		public void displayStudent(Student s) ;
		
		//Displays just names of all students in the database;
		public void DisplayAllNames() throws Exception;
		
		//prints given list of students
		public void displayList(ArrayList<Student> list );

}
