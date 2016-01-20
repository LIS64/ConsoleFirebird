package testShopBook.DAO;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import testShopBook.XML.Book;
import testShopBook.XML.Database;
import testShopBook.XML.Order;
import testShopBook.XML.ParsingDB;
import testShopBook.XML.ParsingFile;
import testShopBook.XML.Shop;

public class ShopBookDAOImpl implements ShopBookDAO{

	@Override
	public Connection getConnectionBD() {
		// TODO Auto-generated method stub
		Database database;
		Connection conn=null;
		ParsingDB DB = new ParsingDB();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse("configBD.xml", DB);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		database = DB.getDatabase();
		if (database!=null){
			try {
				conn = DriverManager.getConnection(database.getUrlDatabase(), database.getLoginDatabase(), database.getPasswordDatabase());
				System.out.println("Подключение к БД произошло успешно");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("При подключении к БД произошла ошибка \nError: "+e.getMessage());
			}
			
		} else {
			System.out.println("Неверный фаил конфигурации");
		}
		return conn;
	}

	@Override
	public void insertDataFromFile(File nameFile) {
		// TODO Auto-generated method stub
		ParsingFile fileParsing = new ParsingFile();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(nameFile, fileParsing);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("При чтении файла произошла ошибка: "+e.getMessage());
		} 
		if (fileParsing.getShopList()!=null||fileParsing.getBookList()!=null||fileParsing.getOrderList()!=null){
			
			try (Connection conn = getConnectionBD();){
			
				if (fileParsing.getShopList()!=null){
				try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO shop_book (shop_name,shop_adress) VALUES (?,?)"); )
				{
				for(Shop shop : fileParsing.getShopList()){
					stmt.setString(1, shop.getShopName());
					stmt.setString(2, shop.getShopAdress());
					stmt.executeUpdate();
				System.out.println("Запись успешно вставлена в базу данных в таблицу SHOP!");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Произошла ошибка при вставке данных в таблицу SHOP! "+ e.getMessage());
		}
		
		} 
		if (fileParsing.getBookList()!=null){
			try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO book (book_name, author, price) VALUES (?,?,?)"); )
					{
						for(Book book : fileParsing.getBookList()){
							stmt.setString(1, book.getBookName());
							stmt.setString(2, book.getAuthor());
							stmt.setInt(3, book.getPrice());
							stmt.executeUpdate();
						System.out.println("Запись успешно вставлена в базу данных в таблицу BOOK!");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Произошла ошибка при вставке данных в таблицу BOOK! "+ e.getMessage());
				}
				
				} 
		if (fileParsing.getOrderList()!=null){
			try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (shop_id, article, count_order) VALUES (?,?,?)"); )
				{
						for(Order order : fileParsing.getOrderList()){
							stmt.setInt(1, order.getShopId());
							stmt.setInt(2, order.getArticle());
							stmt.setInt(3, order.getCountOrder());
							stmt.executeUpdate();
						System.out.println("Запись успешно вставлена в базу данных в таблицу ORDERS!");
						}
							
				} catch (SQLException e) {
							// TODO Auto-generated catch block
					System.out.println("Произошла ошибка при вставке данных в таблицу ORDERS! "+ e.getMessage());
				}
						
				} 
				} catch (SQLException e) {
				// TODO: handle exception
				}
				} else {
					System.out.println("Указанный фаил имеет неправильный формат");
				}	
	}

	@Override
	public void viewsTable(String tableName) {
		// TODO Auto-generated method stub
		if (tableName.equals("shop")||tableName.equals("book")||tableName.equals("orders")){
		try (Connection conn = getConnectionBD();
			Statement stmt = conn.createStatement();)
		{
			if (tableName.equals("shop")){
				ResultSet result = stmt.executeQuery("SELECT * FROM SHOP_BOOK");
				System.out.println("Номер магазина || Название магазина || Адрес магазина");
				while (result.next()){
					int shopId = result.getInt("SHOP_ID");
					String shopName = result.getString("SHOP_NAME");
					String shopAdress = result.getString("SHOP_ADRESS");
					System.out.println(shopId + "||" + shopName+ "||"+shopAdress);
				}
				
			}
			
			else if (tableName.equals("book")){
				ResultSet result = stmt.executeQuery("SELECT * FROM BOOK");
				System.out.println("Артикул || Название книги || Автор || Цена");
				while (result.next()){
					int article = result.getInt("ARTICLE");
					String bookName = result.getString("BOOK_NAME");
					String author = result.getString("AUTHOR");
					int price = result.getInt("PRICE");
					System.out.println(article + "||" + bookName+ "||"+author+ "||"+ price);
				}
				
			}
			
			else if (tableName.equals("orders")){
				ResultSet result = stmt.executeQuery("SELECT * FROM ORDERS");
				System.out.println("Номер заказа || Номер магазина || Артикул || Количество");
				while (result.next()){
					int orderId = result.getInt("ORDER_ID");
					String shopId = result.getString("SHOP_ID");
					String article = result.getString("ARTICLE");
					String countOrder = result.getString("COUNT_ORDER");
					System.out.println(orderId + "," + shopId+ ","+article+ ","+ countOrder);
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("При выполнении запроса произошла ошибка: "+e.getMessage());
		}
		} else {
			System.out.println("Вы ввели имя несущствующей базы данных, попробуйте ещё раз");
		}
		}

	@Override
	public void insertDataFromString(Object obj, String table) {
		// TODO Auto-generated method stub
		if (table.equals("shop")||table.equals("book")||table.equals("orders")){
			try (Connection conn = getConnectionBD();) {
		
		if (table.equals("shop")){
			Shop shop = (Shop) obj;
			try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO shop_book (shop_name,shop_adress) VALUES (?,?)"); )
					{
						stmt.setString(1, shop.getShopName());
						stmt.setString(2, shop.getShopAdress());
						stmt.executeUpdate();
						System.out.println("Запись успешно вставлена в базу данных в таблицу SHOP!");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Произошла ошибка при вставке данных в таблицу SHOP! "+ e.getMessage());
				}
		}  else if (table.equals("book")){
			Book book = (Book) obj;
			try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO book (book_name, author, price) VALUES (?,?,?)"); )
				{
						stmt.setString(1, book.getBookName());
						stmt.setString(2, book.getAuthor());
						stmt.setInt(3, book.getPrice());
						stmt.executeUpdate();
						System.out.println("Запись успешно вставлена в базу данных в таблицу BOOK!");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Произошла ошибка при вставке данных в таблицу BOOK! "+ e.getMessage());
				}
		} else if (table.equals("orders")){
			Order order = (Order) obj;
			try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (shop_id, article, count_order) VALUES (?,?,?)"); )
				{
						stmt.setInt(1, order.getShopId());
						stmt.setInt(2, order.getArticle());
						stmt.setInt(3, order.getCountOrder());
						stmt.executeUpdate();
						System.out.println("Запись успешно вставлена в базу данных в таблицу ORDERS!");
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Произошла ошибка при вставке данных в таблицу ORDERS! "+ e.getMessage());
				}
		} 	
		} catch (SQLException e) {
			// TODO: handle exception
		}
		} else {
			System.out.println("Вы ввели неверный запрос, обратитесь к команде help");
		}

	}

	@Override
	public void customViews(String shopId) {
		// TODO Auto-generated method stub
		try (Connection conn = getConnectionBD();) {
		if (!shopId.isEmpty()){
			try (PreparedStatement stmt = conn.prepareStatement("SELECT shop_book.shop_name, shop_book.shop_adress, orders.count_order, book.price "
					 	    + "FROM shop_book INNER JOIN orders ON shop_book.shop_id = orders.shop_id "
					 		+ "JOIN book ON book.article = orders.article WHERE shop_book.shop_id=?;")) {
				stmt.setInt(1, Integer.parseInt(shopId));
				ResultSet result = stmt.executeQuery();
				System.out.println("Название магазина || Адресс магазина || Общая сумма заказа");
				while (result.next()){
					String shopName = result.getString("SHOP_NAME");
					String shopAdress = result.getString("SHOP_ADRESS");
					int count_Order = result.getInt("COUNT_ORDER");
					int bookPrice = result.getInt("PRICE");
					
					System.out.println(shopName+"||"+shopAdress+"||"+(count_Order*bookPrice));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		} else {
			try(Statement stmt = conn.createStatement();) {
			ResultSet result = stmt.executeQuery("SELECT shop_book.shop_name, shop_book.shop_adress, orders.count_order, book.price "
					 	    + "FROM shop_book INNER JOIN orders ON shop_book.shop_id = orders.shop_id "
					 		+ "JOIN book ON book.article = orders.article;");
			
			System.out.println("Название магазина || Адресс магазина || Общая сумма заказа");
			while (result.next()){
				String shopName = result.getString("SHOP_NAME");
				String shopAdress = result.getString("SHOP_ADRESS");
				int count_Order = result.getInt("COUNT_ORDER");
				int bookPrice = result.getInt("PRICE");
				
				System.out.println(shopName+"||"+shopAdress+"||"+(count_Order*bookPrice));
			}
				
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();

		}
	}

}
