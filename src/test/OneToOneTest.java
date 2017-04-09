package test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.cpp.dao.BaseHibernateDao;
import com.cpp.modle.Bankcard;
import com.cpp.modle.Employee;
import com.cpp.modle.User;
import com.cpp.util.HibernateSessionFactory;

public class OneToOneTest extends BaseHibernateDao{
	
	
	public Set<Bankcard> getCardByEid(int Eid){
		Employee e= (Employee) super.get(Employee.class,Eid);
		Set<Bankcard> cards=e.getBankcards();
		Iterator i=cards.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
		return cards;
	}
	
	@Test
	public void Test(){
		Set<Bankcard> cards=getCardByEid(1);
		
	}
	
	public void limitQuery(){
		Session session=HibernateSessionFactory.getSession();
		
		Query query=session.createQuery("from User");
		query.setFirstResult(1);
		query.setMaxResults(2);
		
		List<User> lists=query.list();
		for(User u:lists){
			System.out.println(u.toString());
		}
		
		
	}
}
