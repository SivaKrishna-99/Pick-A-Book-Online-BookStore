package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;
	private OrderDao orderDao;
	private CustomerDao customerDao;
	private LineItemDao lineItemDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	public void setOrderDao(OrderDao orderDao) { this.orderDao = orderDao; }
	public void setCustomerDao(CustomerDao customerDao) { this.customerDao = customerDao; }
	public void setLineItemDao(LineItemDao lineItemDao) { this.lineItemDao = lineItemDao; }

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}



	private void validateCustomer(CustomerForm customerForm) {

    	String name = customerForm.getName();
		String address = customerForm.getAddress();
		String phone = customerForm.getPhone();
		String email = customerForm.getEmail();
		String creditCard = customerForm.getCcNumber();
		creditCard = formatCard(creditCard);
		phone = formatPhoneNumber(phone);

		if (name == null || name.equals("") || name.length() < 4 || name.length() > 45) {
			throw new ApiException.InvalidParameter("Invalid name field");
		}

		if (address == null || address.equals("") || address.length() < 4 || address.length() > 45) {
			throw new ApiException.InvalidParameter("Invalid name field");
		}

		if (phone == null || phone.equals("") || phone.length() != 10) {
			throw new ApiException.InvalidParameter("Invalid phone field");
		}

		if (email == null || email.equals("") || email.contains(" ") || !email
				.contains("@") || (email.charAt(email.length() - 1) == '.')) {
			throw new ApiException.InvalidParameter("Invalid email field");
		}

		if(creditCard == null || creditCard.equals("") || !StringUtils.isStrictlyNumeric(creditCard) ||
				creditCard.length() < 14 || creditCard.length() > 16)  {
			throw new ApiException.InvalidParameter("Invalid creditcard field");
		}


		if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
			throw new ApiException.InvalidParameter("Invalid expiry date");

		}
	}

	private String formatPhoneNumber(String phone) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < phone.length(); i++) {
			Character ch = phone.charAt(i);
			if (Character.isDigit(ch)) {
				stringBuilder.append(ch);
			}
		}
		return stringBuilder.toString();
	}


	private String formatCard(String creditCard) {

		return creditCard.replaceAll("-", "").replaceAll(" ", "");
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {
		// HINT: Use Integer.parseInt and the YearMonth class
		int month;
		int year;
		try{
			month = Integer.parseInt(ccExpiryMonth);
			year = Integer.parseInt(ccExpiryYear);
		}
		catch(Exception e){
			return true;
		}
		LocalDate now = LocalDate.now();
		if(month < now.getMonthValue() || month > 12 || year < now.getYear()){
			return true;
		}
		return false;

	}

	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() <= 0) {
			throw new ApiException.InvalidParameter("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 0 || item.getQuantity() > 99) {
				throw new ApiException.InvalidParameter("Invalid quantity");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());
			int itemPrice = item.getBookForm().getPrice();
			if (itemPrice != databaseBook.getPrice()) {
				throw new ApiException.InvalidParameter(
						"Invalid Book Price :" + databaseBook.getTitle()
				);
			}
				long itemCategoryId = item.getBookForm().getCategoryId();
				if (itemCategoryId != databaseBook.getCategoryId()) {
					throw new ApiException.InvalidParameter("Invalid Book Category");
				}
		});
	}
	@Override
	public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}
	}
//	https://www.tabnine.com/code/java/methods/java.util.Calendar/getInstance
	private Date getDate(String monthString, String yearString) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, Integer.parseInt(monthString));
		calendar.set(Calendar.YEAR, Integer.parseInt(yearString));
		Date date = calendar.getTime();
		return date;
	}
	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, customerOrderId,
						item.getBookId(), item.getQuantity());
			}
//			Manually commit
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
//				roll it back(as the transaction shoul follow ACID property)
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private Integer generateConfirmationNumber(){
		Random random = new Random();
		return random.nextInt(999999999);
	}

}
