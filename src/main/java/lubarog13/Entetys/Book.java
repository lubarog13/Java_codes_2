package lubarog13.Entetys;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Date;
import java.util.Objects;

public class Book
{
    private int id;
    private String title;
    private String author;
    private int pages;
    private Date createDate;
    private double rating;
    private int ageRating;
    private ImageIcon image;

    public Book( int id, String title, String author, int pages, Date createDate, double rating, int ageRating) {
        this.id = -1;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.createDate = createDate;
        this.rating = rating;
        this.ageRating = ageRating;
    }

    public Book(String title, String author, int pages, Date createDate, double rating, int ageRating) {
        this(-1, title, author, pages, createDate, rating, ageRating);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", createDate=" + createDate +
                ", rating=" + rating +
                ", ageRating=" + ageRating +
                '}';
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && pages == book.pages && Double.compare(book.rating, rating) == 0 && ageRating == book.ageRating && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(createDate, book.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, pages, createDate, rating, ageRating);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(int ageRating) {
        this.ageRating = ageRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
