
import com.dealership.repo.*;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DAOTest {

	private UserDAO dao;
	
	@Before
	public void setup()
	{
		dao = new UserDAO();
	}
	
	@Test
	public void testIsUser()
	{
		assertTrue(dao.isUser("gungy"));
	}
}
