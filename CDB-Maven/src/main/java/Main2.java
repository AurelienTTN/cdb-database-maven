import com.excilys.exceptions.ExceptionComputerVide;
import com.excilys.persistence.Dao;
import com.excilys.ui.MenuCLI;

public class Main2 {

	public static void main(String[] args) throws ExceptionComputerVide {
		
		
		
		MenuCLI menu = MenuCLI.getInstance();
		while(true) {
			menu.useMenu();
		}
		
	}

}
