package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.Student;

public interface StudentDaoInterface {
	
	public boolean addStudent(Student s) throws Exception;
	public Student getStudent(int roll) throws Exception;
	
	public ArrayList<Student> getStudentByName(String name) throws Exception;
	public ArrayList<Student> allStudentsDetails() throws Exception;
	public ArrayList<String> allStudentsName() throws Exception;
	
	public boolean deleteStudent(int roll ) throws Exception;
	
	public boolean  updateName(int reg,String name) throws Exception;
	public boolean  updateAge(int reg,int age) throws Exception;
	public boolean  updateRoll(int reg,int roll) throws SQLException, Exception;
	public boolean  updateDob(int reg,String name) throws Exception;
	public boolean  updateFathersName(int reg,String name) throws Exception;
	public boolean  updateMothersName(int reg,String name) throws Exception;
	public boolean  updateLocalGuardian(int reg,String name) throws Exception;
	public boolean  updateEmergencyContact(int reg,String name) throws Exception;
	public boolean  updateLocality(int reg,String name) throws Exception;
	public boolean  updateCity(int reg,String name) throws Exception;
	public boolean  updateState(int reg,String name) throws Exception;
	
	public int checkRegNo(int roll) throws Exception;
	

}
