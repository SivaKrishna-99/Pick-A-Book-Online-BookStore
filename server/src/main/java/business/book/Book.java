package business.book;

public class Book {

    private long bookId;
    private String title;
    private String author;
    private String description;
    private int price;
    private int rating;
    private boolean isPublic;
    private boolean isFeatured;
    private long categoryId;


    public Book(long bookId, String title, String author, String description, int price, int rating, boolean isPublic, boolean isFeatured, long categoryId) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.isPublic = isPublic;
        this.isFeatured = isFeatured;
        this.categoryId = categoryId;
    }


    public String getTitle() {return title;}

    public String getAuthor() {return author;}

    public String getDescription() {return description;}

    public int getPrice() {return price;}
    public long getBookId() {return bookId;}

    public int getRating() {return rating;}

    public boolean getIsPublic() {return isPublic;}

    public boolean getIsFeatured() {return isFeatured;}

    public long getCategoryId() {return categoryId;}


    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", isPublic=" + isPublic +
                ", isFeatured=" + isFeatured +
                ", categoryId=" + categoryId +
                '}';
    }
}
