package testShopBook.XML;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParsingFile extends DefaultHandler {

	private ArrayList<Shop> shopList = new ArrayList<>();
	private ArrayList<Book> bookList = new ArrayList<>();
	private ArrayList<Order> orderList = new ArrayList<>();
	private Shop shop;
	private Book book;
	private Order order;
	private boolean isShop, isBook, isOrder;
	private String findTag;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		findTag = qName;
		switch (findTag){
		case "shop":
			shop = new Shop();
			isShop=true;
			break;
		case "book":
			book = new Book();
			isBook=true;
			break;
		case "order":
			order = new Order();
			isOrder=true;
			break;	
		
		}
		
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		String body = new String(ch, start, length);
		if (isShop && !body.equals("\n")){
			switch (findTag) {
			case "shop-name":
				shop.setShopName(body);
				break;
			case "shop-adress":
				shop.setShopAdress(body);
				break;
		
			}
		} else if (isBook && !body.equals("\n")){
			switch (findTag) {
			case "book-name":
				book.setBookName(body);
				break;
			case "book-author":
				book.setAuthor(body);
				break;
			case "book-price":
				book.setPrice(Integer.parseInt(body));
				break;
		
			}
		} else if (isOrder && !body.equals("\n")){
			switch (findTag) {
			case "order-shop-id":
				order.setShopId(Integer.parseInt(body));
				break;
			case "order-article":
				order.setArticle(Integer.parseInt(body));
				break;
			case "order-count":
				order.setCountOrder(Integer.parseInt(body));
				break;
			}
		}
	
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		if (qName.equals("shop")){
			shopList.add(shop);
			findTag="";
			isShop=false;
			shop=null;
		} else if (qName.equals("book")){
			bookList.add(book);
			findTag="";
			isBook=false;
			book=null;
		} else if (qName.equals("order")){
			orderList.add(order);
			findTag="";
			isOrder=false;
			order=null;
		}
	}
	
	public ArrayList<Shop> getShopList(){
		if (shopList.isEmpty())
		{
			return null;
		} else {
			return shopList;
		}
	}
	
	public ArrayList<Book> getBookList(){
		if (bookList.isEmpty())
		{
			return null;
		} else {
			return bookList;
		}
	}
	
	public ArrayList<Order> getOrderList(){
		if (orderList.isEmpty())
		{
			return null;
		} else {
			return orderList;
		}
	}
	
}
