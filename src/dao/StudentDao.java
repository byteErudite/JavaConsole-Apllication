package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import bean.Student;

public class StudentDao implements StudentDaoInterface
{
	static Connection con ;
	//get a Connection object from database
	public Connection connect() 
	{
		con = null ;
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			try 
			{
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vaibhav","root","vaibhav");
			} 
			catch (SQLException e)
			{
				System.out.println("connection to database could not be established\n");
				e.printStackTrace();
			}
		} 
		catch (ClassNotFoundException e)
		{
			System.out.println("Driver class could not be loaded\n"+e.getStackTrace());
		}
		return con;
	}
	
	//get a statement object  with connection to database
	public Statement getStatement()
	{
		Statement st = null;
		con = connect();
		try 
		{
			st = con.createStatement();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return st;
	}
	
	public PreparedStatement getPreaparedStatement()
	{
		con = connect();
		PreparedStatement pst1 = null;
		try
		{
			pst1 = con.prepareStatement("INSERT INTO student(name,age,Roll,DOB) VALUES ( ?, ?, ?, ?)");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return pst1; 
	}
	
	//sorts the students by roll no after each addition and deletion
	public void sort() throws Exception
	{
		int c=1;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		String name = null;
		
		
		try
		{
			con = connect();
			st = getStatement();
			
			//saving all names in dictionary order and assigning roll by c value 
			//starting from c=1 and going until all names are assigned Rolls
			rs = st.executeQuery("select name from student order by name");
			while(rs.next())
			{
				pst = con.prepareStatement("update student set roll = ? where name = ?");
				name = rs.getString("name");
				pst.setInt(1, c);
				pst.setString(2, name);
				c++;
				pst.executeUpdate();
			}
		}
		catch(Exception e)
		{
			System.out.println("Error in sorting elements\n"+e.getStackTrace());
		}
		finally
		{
			con.close();
			pst.close();
			st.close();
			rs.close();
		}
	}
	
	//Add a new Student to database
	
	public boolean addStudent(Student s) throws Exception
	{
		//creating prepared statements to execute insert queries
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		Statement st = null;
		Date date = null;
		String defaultDate = "1996-10-10";
		java.util.Date date1 = null;
		SimpleDateFormat sdf = null;
		ResultSet rs = null;
		
		//converting students string date to SQL Date
		try
		{
			sdf = new SimpleDateFormat("yyyy-mm-dd");
			date1 = sdf.parse(s.dob.getDOB());
			date = new Date(date1.getTime());
		}
		catch(Exception e)
		{
			System.out.println("Error in converting given date into yyyy-dd-mm format\n");
			System.out.println("Default date 1996-10-10 is set \nPlease update later ");
			sdf = new SimpleDateFormat("yyyy-mm-dd");
			date1 = sdf.parse(defaultDate);
			date = new Date(date1.getTime());
		}
		
		
		
		
		try
		{
			//Creating connection object
			System.out.println("P1");
			con = connect();
			System.out.println("P2");
			//Insert Query for student
			pst1 = con.prepareStatement("INSERT INTO student(name,age,Roll,DOB) VALUES ( ?, ?, ?, ?)");
			System.out.println("P3");
			
			//Setting attributes of above Queries
			pst1.setString(1, s.getName());
			pst1.setInt(2, s.getAge());
			pst1.setInt(3, s.getRollNo());
			pst1.setDate(4, date);
			System.out.println("P4");
			//Executing Insert Query for student 
			pst1.executeUpdate();
			System.out.println("P5");
			//Queries for 
			//->insertion in parents_details and address tables respectively
			st = con.createStatement();
			System.out.println("P6");
			pst2 = con.prepareStatement("INSERT INTO parents_details(fathersName,mothersName,localGuardian,emergencyContactNo,regNo) VALUES (?, ?, ?, ?, ?)");
			pst3 = con.prepareStatement("INSERT INTO address(locality,city,state,pinCode,regNo) VALUES ( ? ,?, ?, ?, ?)");
			System.out.println("P7");
			//getting Registration  No from student table
			int regNo =-1;
			try{
				System.out.println("P8");
			rs = st.executeQuery("select regNo from student order by regNo desc limit 1 ;");
			rs.next();
			regNo = rs.getInt("regNo");
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				System.out.println(e.getLocalizedMessage());
			}
			
			System.out.println("Registration Number Returned from student Table : "+regNo);
			pst2.setString(1, s.parents.getFathersName());
			pst2.setString(2, s.parents.getMothersName());
			pst2.setString(3, s.parents.getLocalGuardian());
			pst2.setString(4, s.parents.getEmergencyContactNo());
			pst2.setInt(5, regNo);
			pst3.setString(1,s.address.getLocality());
			pst3.setString(2,s.address.getCity());
			pst3.setString(3,s.address.getState());
			pst3.setInt(4,s.address.getPinCode());
			pst3.setInt(5, regNo);
			
			//executing insert in parents_details and address tables
			pst2.executeUpdate();
			pst3.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("Error Ocuured in inserting student in database\n "+e.getStackTrace());
		}
		finally
		{
			con.close();
			st.close();
			pst1.close();
			pst2.close();
			pst3.close();
		}
		sort();
		return true;
			
	}
	
	//Returns a Student from database searched through his Roll
	public Student getStudent(int roll) throws Exception
	{
		Student s = new Student();
		Statement st = null;
		ResultSet rs = null;
		
		try
		{
			st = getStatement();
			rs = st.executeQuery("select *  from student where Roll ='"+roll+"'" );
			if(rs.next())
			{
				s.setName(rs.getString("name"));
				s.setRollNo(rs.getInt("Roll"));
				s.setAge(rs.getInt("age"));
				s.dob.setDOB(rs.getString("DOB"));
			}
		}
		catch(Exception e)
		{
			System.out.println("Student record could not be fetched \n"+e.getStackTrace());
		}
		finally
		{
			con.close();
			st.close();
			rs.close();
		}
		return s;
	}
	
	//Returns a list of Students from database starting with string provided 
	public ArrayList<Student> getStudentByName(String name) throws Exception
	{
		ArrayList<Student> list = new ArrayList<Student>();
		String query = "select * from student where name like ? ";
		PreparedStatement p = null;
		ResultSet rs = null;
		
		try
		{
			con = connect();
			p = con.prepareStatement(query);
			p.setString(1,"%"+ name +"%");
			rs = p.executeQuery();
			if(rs==null)
				System.out.println("null");
		
			while(rs.next())
			{
				Student s = new Student();
				s.setName(rs.getString("name"));
				s.setRollNo(rs.getInt("Roll"));
				s.setAge(rs.getInt("age"));
				s.dob.setDOB(rs.getString("DOB"));
				list.add(s);
			}
		}
		catch(Exception e)
		{
			System.out.println("Error in fetching students list \n"+e.getStackTrace());
			
		}
		finally
		{
			con.close();
			p.close();
			rs.close();
		}
		return list;
	}
	
	
	//Returns list of strings containing all students names from database
	public ArrayList<String> allStudentsName() throws Exception
	{
		ArrayList<String> list = new ArrayList<String>();
		Statement st = null;
		ResultSet rs = null;
		
		
		try
		{
			st = getStatement();
			rs = st.executeQuery("select name from student order by name ");
			while(rs.next())
			{
				list.add(rs.getString("name"));
			}
		}
		catch(Exception e)
		{
			System.out.println("Error list could not be retrieved\n"+e.getStackTrace());
		}
		finally
		{
			con.close();
			st.close();
			rs.close();
		}
		
		return list;
	}
	
	//returns list of student objects containing all students  from database
	public ArrayList<Student> allStudentsDetails() throws Exception
	{
		ArrayList<Student> list = new ArrayList<Student>();
		Statement st = null;
		ResultSet rs = null;
		
		try
		{
			st = getStatement();
			rs = st.executeQuery("SELECT s.`regNo`,Roll,NAME,age,DOB,locality,city,state,pinCode,fathersName,mothersName,localGuardian,emergencyContactNo FROM student s, address a,parents_details p WHERE s.`regNo`=a.`regNo` AND s.`regNo`=p.`regNo` ORDER BY s.`Roll`;");
			while(rs.next())
			{
				Student s = new Student();
				s.setName(rs.getString("name"));
				s.setRollNo(rs.getInt("Roll"));
				s.setAge(rs.getInt("age"));
				s.dob.setDOB(rs.getString("DOB"));
				s.address.setLocality(rs.getString("locality"));
				s.address.setCity(rs.getString("city"));
				s.address.setState(rs.getString("state"));
				s.address.setPinCode(rs.getInt("pinCode"));
				s.parents.setFathersName(rs.getString("fathersName"));
				s.parents.setMothersName(rs.getString("mothersName"));
				s.parents.setLocalGuardian(rs.getString("localGuardian"));
				s.parents.setEmergencyContactNo(rs.getString("emergencyContactNo"));
				list.add(s);
			}
		}
		catch(Exception e)
		{
			System.out.println("Data could not be fetched\n"+e.getStackTrace());
		}
		finally{
			con.close();
			st.close();
			rs.close();
		}
		return list;
	}
		
	//deletes a student using Roll no
	public boolean deleteStudent(int roll) throws Exception 
	{
		Statement st1 = null;
		Statement st2 = null;
		Statement st3 = null;
		Statement st4 = null;
		ResultSet rs = null;
		int regno ;
		
		try 
		{
			st1 = getStatement();
			rs = st1.executeQuery("select regNo  from student where Roll='"+roll+"'");
			rs.next();
			regno = rs.getInt("regNo");
			st2 = getStatement();
			st3 = getStatement();
			st4 = getStatement();
			
			
			st2.executeUpdate("delete from address where regNo='"+regno+"'");
			st3.executeUpdate("delete from parents_details where regNo='"+regno+"'");
			st4.executeUpdate("delete from student where regNo='"+regno+"'");
			
			sort();
			return true;
			
		}
		catch (SQLException e)
		{
			System.out.println("Deletion was unsuccessful");
			return false;
		}
		finally
		{
			con.close();
			st1.close();
			st2.close();
			st3.close();
			st4.close();
			rs.close();
		}
		
	}
	
	//Updation methods for all fields , Return true if updation is successful 
	public boolean  updateName(int reg,String name) throws Exception
	{
		String query = null;
		PreparedStatement p = null;
		
		try
		{
			con=connect();
			query = "update student set name = ? where regNo = ?";
			p = con.prepareStatement(query);
			p.setString(1, name);
			p.setInt(2, reg);
			if(p.executeUpdate()>0){
				sort();
				return true;}
			else 
				return false;
		}
		catch(Exception e)
		{
			System.out.println("Name could not be updated \n"+e.getStackTrace());
			return false;
		}
		finally
		{
			con.close();
			p.close();
		}
	}
	public boolean  updateAge(int reg,int age) throws Exception
	{
		String query = null;
		PreparedStatement p = null;
		
		try
		{
			con=connect();
			query = "update student set Age = ? where regNo = ?";
			p = con.prepareStatement(query);
			p.setInt(1, age);
			p.setInt(2, reg);
			if(p.executeUpdate()>0)
				return true;	
			else 
				return false;
		}
		catch(Exception e)
		{
			System.out.println("Age could not be updated \n"+e.getStackTrace());
			return false;
		}
		finally
		{
			con.close();
			p.close();
		}
	}
	public boolean  updateRoll(int reg,int roll) throws Exception
	{
		String query = null;
		PreparedStatement p = null;
		
		try
		{
			con=connect();
			query = "update student set Roll = ? where regNo = ?";
			p = con.prepareStatement(query);
			p.setInt(1, roll);
			p.setInt(2, reg);
			if(p.executeUpdate()>0)
			{
				sort();
				return true;
			}
			else 
			return false;
		}
		catch(Exception e)
		{
			System.out.println("Roll could not be updated \n"+e.getStackTrace());
			return false;
		}
		finally
		{
			con.close();
			p.close();
		}
	}
	public boolean  updateDob(int reg,String dob) throws Exception
	{
		String query = null;
		PreparedStatement p = null;
		Date date = null;
		
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			java.util.Date date1 = sdf.parse(dob);
			date = new Date(date1.getTime());
		}
		catch(Exception e)
		{
			System.out.println("String provided as date could not be converted to yyyy-mm-dd format\n");
			return false;
		}
		try
		{
			con=connect();
			query = "update student set DOB = ? where regNo = ?";
			p = con.prepareStatement(query);
			p.setDate(1, date);
			p.setInt(2, reg);
			if(p.executeUpdate()>0)
				return true;
			else 
				return false;
		}
		catch(Exception e)
		{
			System.out.println("Dob could not be updated");
			return false;
		}
		finally
		{
			con.close();
			p.close();
		}
	}
	public boolean  updateFathersName(int reg,String name) throws Exception
	{
		String query = null;
		PreparedStatement p = null;
		
		try
		{
			con=connect();
			query = "update parents_details set fathersName = ? where regNo = ?";
			p = con.prepareStatement(query);
			p.setString(1, name);
			p.setInt(2, reg);
			if(p.executeUpdate()>0)
				return true;
			else 
				return false;
		}
		catch(Exception e)
		{
			System.out.println("Father's Name  could not be updated \n"+e.getStackTrace());
			return false;
		}
		finally
		{
			con.close();
			p.close();
		}
	}
	public boolean  updateMothersName(int reg,String name) throws Exception
	{
		String query = null;
		PreparedStatement p = null;
		
		try
		{
			con=connect();
			query = "update parents_details set mothersName = ? where regNo = ?";
			p = con.prepareStatement(query);
			p.setString(1, name);
			p.setInt(2, reg);
			if(p.executeUpdate()>0)
				return true;
			else 
				return false;
		}
		catch(Exception e)
		{
			System.out.println("Mother's Name  could not be updated \n"+e.getStackTrace());
			return false;
		}
		finally
		{
			con.close();
			p.close();
		}
	}
	public boolean  updateLocalGuardian(int reg,String name) throws Exception
	{
		String query = null;
		PreparedStatement p = null;
		
		try
		{
			con=connect();
			query = "update parents_details set localGuardian = ? where regNo = ?";
			p = con.prepareStatement(query);
			p.setString(1, name);
			p.setInt(2, reg);
			if(p.executeUpdate()>0)
				return true;
			else 
				return false;
		}
		catch(Exception e)
		{
			System.out.println("LocalGuardian's Name  could not be updated \n"+e.getStackTrace());
			return false;
		}
		finally
		{
			con.close();
			p.close();
		}
	}
	public boolean  updateEmergencyContact(int reg,String name) throws Exception
	{
		String query = null;
		PreparedStatement p = null;
		
		try
		{
			con=connect();
			query = "update parents_details set emergencyContactNo = ? where regNo = ?";
			p = con.prepareStatement(query);
			p.setString(1, name);
			p.setInt(2, reg);
			if(p.executeUpdate()>0)
				return true;
			else 
				return false;
		}
		catch(Exception e)
		{
			System.out.println("EmergencyContact  could not be updated \n"+e.getStackTrace());
			return false;
		}
		finally
		{
			con.close();
			p.close();
		}
	}
	public boolean  updateLocality(int reg,String name) throws Exception
	{
		String query = null;
		PreparedStatement p = null;
		
		try
		{
			con=connect();
			query = "update address set locality = ? where regNo = ?";
			p = con.prepareStatement(query);
			p.setString(1, name);
			p.setInt(2, reg);
			if(p.executeUpdate()>0)
				return true;
			else 
				return false;
		}
		catch(Exception e)
		{
			System.out.println("Locality  could not be updated \n"+e.getStackTrace());
			return false;
		}
		finally
		{
			con.close();
			p.close();
		}
	}
	public boolean  updateCity(int reg,String name) throws Exception
	{
		String query = null;
		PreparedStatement p = null;
		
		try
		{
		con=connect();
		query = "update address set city = ? where regNo = ?";
		p = con.prepareStatement(query);
		p.setString(1, name);
		p.setInt(2, reg);
		if(p.executeUpdate()>0)
			return true;
		else 
			return false;
		}
		catch(Exception e)
		{
			System.out.println("City could not be updated \n"+e.getStackTrace());
			return false;
		}
		finally
		{
			con.close();
			p.close();
		}
	}
	public boolean  updateState(int reg,String name) throws Exception
	{
		String query = null;
		PreparedStatement p = null;
		
		try
		{
			con=connect();
			query = "update address set state = ? where regNo = ?";
			p = con.prepareStatement(query);
			p.setString(1, name);
			p.setInt(2, reg);
			if(p.executeUpdate()>0)
				return true;
			else 
				return false;
		}
		catch(Exception e)
		{
			System.out.println("State could not be updated \n"+e.getStackTrace());
			return false;
		}
		finally
		{
			con.close();
			p.close();
		}
	}
	
	//Returns Registration number of student mapped to given Roll no
	public int checkRegNo(int roll) throws Exception
	{
		Statement st = null;
		ResultSet rs = null;
		
		try
		{
			st = getStatement();
			rs = st.executeQuery("select regNo from student where Roll = '"+roll+"'");
			rs.next();
			return rs.getInt("regNo");
		}
		catch(Exception e)
		{
			System.out.println("Error in locating Registartion number\n"+e.getStackTrace());
			return -1;
		}
		finally
		{
			st.close();
			con.close();
			rs.close();
		}
	}
}

