import com.excilys.persistence.Dao;

public class Main2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dao test = Dao.getInstance();
		test.connection();
		System.out.println(test.oneComputer(29));
		
	}

}
