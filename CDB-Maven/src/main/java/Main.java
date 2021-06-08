import java.io.IOException;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.config.AppConfig;
import com.excilys.exceptions.ExceptionComputerVide;
import com.excilys.persistence.Dao;
import com.excilys.ui.MenuCLI;


/*
@Configuration 
@ComponentScan({"com.excilys.controlers","com.excilys.mapper","com.excilys.persistence","com.excilys.service","com.excilys.ui","com.excilys.validator"})*/


public class Main {

	public static void main(String[] args) throws IOException, SQLException, ExceptionComputerVide {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		MenuCLI menu = context.getBean(MenuCLI.class);
		while(true) {
			menu.useMenu();
		}
	}
}

