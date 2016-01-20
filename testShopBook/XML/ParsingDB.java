package testShopBook.XML;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParsingDB extends DefaultHandler {
	
	private Database db;
	private String findTag;
	
	public ParsingDB() {
		// TODO Auto-generated constructor stub
		db = new Database();
	}
	
	public Database getDatabase(){
		return db;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		findTag = qName;
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		String body = new String(ch, start, length);
		
		switch (findTag) {
		case "urlBD":
			db.setUrlDatabase(body);
			break;
		case "loginBD":
			db.setLoginDatabase(body);
			break;
		case "passwordBD":
			db.setPasswordDatabase(body);
			break;
		case "JDBCDriver":
			db.setJDBCDriver(body);
			break;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		findTag="";
		}
	
	

	
	

}
