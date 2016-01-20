package testShopBook.DAO;

import java.io.File;
import java.sql.Connection;

public interface ShopBookDAO {
	public Connection getConnectionBD();
	public void insertDataFromFile(File nameFile);
	public void viewsTable(String tableName);
	public void insertDataFromString(Object obj, String table);
	public void customViews(String shopId);
}
