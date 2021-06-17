import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.excilys.config.AppConfig;
import com.excilys.dto.ComputerDTO;
import com.excilys.exceptions.ExceptionComputerVide;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.ComputerJpaDAO;
import com.excilys.persistence.Dao;
import com.excilys.service.ServiceS;
import com.excilys.ui.MenuCLI;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;


/*
@Configuration 
@ComponentScan({"com.excilys.controlers","com.excilys.mapper","com.excilys.persistence","com.excilys.service","com.excilys.ui","com.excilys.validator"})*/


public class Main {

	public static void main(String[] args) throws IOException, SQLException, ExceptionComputerVide {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		ServiceS service = context.getBean(ServiceS.class);
	
		Pageable firstPage = PageRequest.of(0, 10,Sort.by("introduced"));

		String recherche = "Apple";
		List<Computer> computers = service.getListeComputerByNameOrderBy("%"+recherche+"%",firstPage);
		int nb_element = service.getNbElementListeSearch("%"+recherche+"%");
		System.out.println("nb element : "+ nb_element);
		
		for(Computer c: computers) {
			System.out.println(c);
		}
		
		/*	
		Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
		Pageable secondPageWithFiveElements = PageRequest.of(1, 5);
		Page<Computer> computerListe = dao.findAll(secondPageWithFiveElements);
		List<Computer> computerListe2 = computerListe.getContent();
		System.out.println(computerListe2);
		
		System.out.println(computer);
		System.out.println("nombre computer :"+nb);*/
	
	}
}

