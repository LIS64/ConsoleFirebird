package testShopBook.XML;

public class Order {
	
	private int orderId;
	private int shopId;
	private int article;
	private int countOrder;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public int getArticle() {
		return article;
	}
	public void setArticle(int article) {
		this.article = article;
	}
	public int getCountOrder() {
		return countOrder;
	}
	public void setCountOrder(int countOrder) {
		this.countOrder = countOrder;
	}

}
