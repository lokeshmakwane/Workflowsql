import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;
import org.springframework.jdbc.BadSqlGrammarException;


public class TestCase {
	
	@Test
	public void TestCase1() throws Exception
	{
		ArrayList aList=new ArrayList();
		aList.add(new String("2:String"));
		TestMain tmain=new TestMain("application-context.xml", "dataSource","transactionManager");
		tmain.setQuery( "select * from  employee where id=? ",aList);
		tmain.executeQuery();
	}
	
	@Test
	public void TestCase2() throws Exception
	{
		ArrayList aList=new ArrayList();
		aList.add(new String("1:String"));
		aList.add("Kapil:String");
		TestMain tmain=new TestMain("application-context.xml", "dataSource","transactionManager");
		tmain.setQuery( "select * from  employee where id=? and name=? ",aList);
		tmain.executeQuery();
		
	}
	
	@Test
	public void TestCase3() throws Exception
	{
		ArrayList aList=new ArrayList();
		TestMain tmain=new TestMain("application-co333333ntext.xml", "dataSource","transactionManager");
		tmain.setQuery( "select * from  employee",aList);
		tmain.executeQuery();
	}
	
	@Test
	public void TestCase4() throws Exception
	{
		ArrayList aList=new ArrayList();
		TestMain tmain=new TestMain("application-context.xml", "dataSource","transactionManager");
		aList.add(new String("6:String"));
		aList.add("Isha:String");
		tmain.setQuery( "insert into employee values(?,?)",aList);
		tmain.executeQuery();
	}
	
	
	@Test
	public void TestCase5() throws Exception
	{
		ArrayList aList=new ArrayList();
		TestMain tmain=new TestMain("application-context.xml", "dataSource","transactionManager");
		aList.add("Nikita:String");
		aList.add(new String("6:String"));
		tmain.setQuery( "update employee  name=? where id=?",aList);
		tmain.executeQuery();
	}
	
	@Test
	public void TestCase6() throws Exception
	{
		ArrayList aList=new ArrayList();
		TestMain tmain=new TestMain("application-context.xml", "dataSource","transactionManager");
		aList.add(new String("6:String"));
		tmain.setQuery( "delete from employee where id=?",aList);
		tmain.executeQuery();
	}
	

}

