import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.Assert;

import com.neustar.workflow.jdbc.ConnectionFactory;
import com.neustar.workflow.jdbc.GetSQLCmdExceute;

/**
 * @author lokesh.makwane
 *
 */
public class TestMain extends GetSQLCmdExceute {
	
	TransactionStatus result;
	
	public TestMain() {
		// TODO Auto-generated constructor stub
	}
	public TestMain(String context, String datasource,	String transcation){
		Assert.notNull(context,"Context Property Can't be Null");
		setBeanName(context);
		ApplicationContext ctxt = new ClassPathXmlApplicationContext(getBeanName());
		Assert.notNull(ctxt,"No Context Loader Found ");
		Assert.notNull(datasource,"Data Source Can't be Null");
		DataSource dsrc = (DataSource) ctxt.getBean(datasource);
		Assert.notNull(dsrc,"No Data Source Found");
		new ConnectionFactory().setDataSource(dsrc);
		setTransactionManager((DataSourceTransactionManager) ctxt.getBean(transcation));
		result=getTransactionManager().getTransaction(getTxTemplate());
	}
	

	public void setQuery( String sqlStmt, List parameters) {
		setParameter(parameters);
		Assert.notNull(sqlStmt,"Query Can't be Null");
		setSqlStatment(sqlStmt);
		try {
			initialize();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void executeQuery() throws SQLException,BadSqlGrammarException {
		Assert.notNull(result,"No Transaction Status set");
		result.createSavepoint();
		try {
			Object obj=super.execute();
			if(!(obj instanceof Integer))
			{
				List list=(List)obj;
				Assert.notEmpty(list,"List Is Empty");
				
				Iterator it=list.iterator();
				while(it.hasNext())
				{
					List lst=(List)it.next();
					System.out.println(lst.get(0)+"   "+lst.get(1));
				}
			}
			else
				System.out.println(obj+" Record Updated");
		} catch (DataAccessException e) {
			// TODO: handle exception
			result.setRollbackOnly();
			throw e;
		}
		//Commit
		getTransactionManager().commit(result);

	}

}
