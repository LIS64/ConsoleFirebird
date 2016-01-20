package testShopBook;

import java.io.File;
import java.util.Scanner;
import testShopBook.DAO.ShopBookDAOImpl;
import testShopBook.XML.Book;
import testShopBook.XML.Order;
import testShopBook.XML.Shop;

public class ShopBook {
	
	static public void help(){
		System.out.println("Вы воспользовались командой help"
				+ "\nДанное приложение реализует следующий функционал:"
				+ "\n\nhelp - команда для получения справки по приложению"
				+ "\n\ninsert file [путь к файлу] - добавляет запси в БД из указаного xml файла;"
				+ "\n\ninsert into [название таблицы] - добавляет запись в БД последовательно заполняя поля указанной таблицы;"
				+ " В качестве аргумента [название таблицы] могут выступать shop, book, orders;"
				+ "\n\nviews [название таблицы] - позволяет просматривать все записи из указанной таблицы;"
				+ " В качестве аргумента [название таблицы] могут выступать shop, book, orders;"
				+ "\n\ncustom views [all или id=?] - позволет выводить сводную информацию, при указании параметра all"
				+ " выводиться информация по всем магазинам, при указании индентификатора конкретного магазина, выводиться информация только по нему;"
				+ "\n\nexit - выход из приложения;");
	}
	
	static public void errorCommand(){
		System.out.println("Вы ввели некорректную команду, воспользуйтесь справкой help");
	}

	public static void main(String[] args) {
		System.out.println("Добро пожаловать в консольное приложение для работы с БД firebird."
				+ "\nДля получения справки введите команду help."
				+ " Для выхода из приложения введите команду exit.");
		Scanner in = new Scanner(System.in);
		boolean isExit=false;
		String inCommand;
		ShopBookDAOImpl shop = new ShopBookDAOImpl();
		while (!isExit){
			inCommand=in.nextLine();
			
			if (inCommand.contains("help")){
				help();
			}
			else if (inCommand.contains("exit")) {
				in.close();
				isExit=true;
			} else if (inCommand.contains("insert file")){
				if (inCommand.contains(".xml")){
					File xmlFile = new File(inCommand.split("insert file ")[1]);
					if (xmlFile.exists()){
						shop.insertDataFromFile(xmlFile);
					} else {
						System.out.println("Вы указали неправильно путь или имя файла!");
					}
				} else {System.out.println("Неверно указан путь к файлу");}
			} else if (inCommand.contains("insert into")){
				String table = inCommand.split("insert into ")[1];
					if (table.equals("shop")){
						Shop sh = new Shop();
						System.out.println("Введите имя магазина: ");
						sh.setShopName(in.nextLine());
						System.out.println("Введите адрес магазина: ");
						sh.setShopAdress(in.nextLine());
						shop.insertDataFromString(sh, table);
					} else if (table.equals("book")){
						Book book = new Book();
						System.out.println("Введите название книжки: ");
						book.setBookName(in.nextLine());
						System.out.println("Введите автора: ");
						book.setAuthor(in.nextLine());
						System.out.println("Введите цену: ");
						book.setPrice(in.nextInt());
						shop.insertDataFromString(book, table);
					} else if (table.equals("orders")) {
						Order order = new Order();
						System.out.println("Введите номер магазина: ");
						order.setShopId(in.nextInt());
						System.out.println("Введите артикл книги: ");
						order.setArticle(in.nextInt());
						System.out.println("Введите количество книг: ");
						order.setCountOrder(in.nextInt());
						shop.insertDataFromString(order, table);
					
				} else {
					System.out.println("Вы указали неверное имя таблицы");
				}
			} 
			else if (inCommand.contains("custom views")){
					if (inCommand.contains("id")){
						if (inCommand.contains("=")){
							String[] shopId = inCommand.split("=");
							if (shopId.length==2)
							shop.customViews(shopId[1].trim());
							else errorCommand();

						}else { errorCommand();}
						} else if (inCommand.contains("all")) {
						shop.customViews("");
						} else {errorCommand();}
			}else if (inCommand.contains("views")){
				shop.viewsTable(inCommand.split("views ")[1]);
			} 
			else if (inCommand.isEmpty()){
			} 
			else {
				errorCommand();
			}
		}	
	}

}
