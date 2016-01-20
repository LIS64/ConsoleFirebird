package testShopBook;

import java.io.File;
import java.util.Scanner;
import testShopBook.DAO.ShopBookDAOImpl;
import testShopBook.XML.Book;
import testShopBook.XML.Order;
import testShopBook.XML.Shop;

public class ShopBook {
	
	static public void help(){
		System.out.println("�� ��������������� �������� help"
				+ "\n������ ���������� ��������� ��������� ����������:"
				+ "\n\nhelp - ������� ��� ��������� ������� �� ����������"
				+ "\n\ninsert file [���� � �����] - ��������� ����� � �� �� ��������� xml �����;"
				+ "\n\ninsert into [�������� �������] - ��������� ������ � �� ��������������� �������� ���� ��������� �������;"
				+ " � �������� ��������� [�������� �������] ����� ��������� shop, book, orders;"
				+ "\n\nviews [�������� �������] - ��������� ������������� ��� ������ �� ��������� �������;"
				+ " � �������� ��������� [�������� �������] ����� ��������� shop, book, orders;"
				+ "\n\ncustom views [all ��� id=?] - �������� �������� ������� ����������, ��� �������� ��������� all"
				+ " ���������� ���������� �� ���� ���������, ��� �������� ��������������� ����������� ��������, ���������� ���������� ������ �� ����;"
				+ "\n\nexit - ����� �� ����������;");
	}
	
	static public void errorCommand(){
		System.out.println("�� ����� ������������ �������, �������������� �������� help");
	}

	public static void main(String[] args) {
		System.out.println("����� ���������� � ���������� ���������� ��� ������ � �� firebird."
				+ "\n��� ��������� ������� ������� ������� help."
				+ " ��� ������ �� ���������� ������� ������� exit.");
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
						System.out.println("�� ������� ����������� ���� ��� ��� �����!");
					}
				} else {System.out.println("������� ������ ���� � �����");}
			} else if (inCommand.contains("insert into")){
				String table = inCommand.split("insert into ")[1];
					if (table.equals("shop")){
						Shop sh = new Shop();
						System.out.println("������� ��� ��������: ");
						sh.setShopName(in.nextLine());
						System.out.println("������� ����� ��������: ");
						sh.setShopAdress(in.nextLine());
						shop.insertDataFromString(sh, table);
					} else if (table.equals("book")){
						Book book = new Book();
						System.out.println("������� �������� ������: ");
						book.setBookName(in.nextLine());
						System.out.println("������� ������: ");
						book.setAuthor(in.nextLine());
						System.out.println("������� ����: ");
						book.setPrice(in.nextInt());
						shop.insertDataFromString(book, table);
					} else if (table.equals("orders")) {
						Order order = new Order();
						System.out.println("������� ����� ��������: ");
						order.setShopId(in.nextInt());
						System.out.println("������� ������ �����: ");
						order.setArticle(in.nextInt());
						System.out.println("������� ���������� ����: ");
						order.setCountOrder(in.nextInt());
						shop.insertDataFromString(order, table);
					
				} else {
					System.out.println("�� ������� �������� ��� �������");
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
