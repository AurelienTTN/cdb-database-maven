import java.io.IOException;
import java.sql.SQLException;

import com.excilys.exceptions.ExceptionComputerVide;
import com.excilys.ui.MenuCLI;




public class Main {

	public static void main(String[] args) throws IOException, SQLException, ExceptionComputerVide {
	
		MenuCLI menu = MenuCLI.getInstance();
		while(true) {
			menu.useMenu();
		}
	}
}

