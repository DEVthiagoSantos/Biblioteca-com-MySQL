package org.example.model;

public class BookModel {

    private int idBook;
    private String author;
    private String title;
    private int totalQuantity;
    private int quantityAvailable;

    public BookModel() {}

    public BookModel(String author,
                     String title,
                     int total_quantity,
                     int quantity_available) {
        this.author = author;
        this.title = title;
        this.totalQuantity = total_quantity;
        this.quantityAvailable = quantity_available;
    }

    public BookModel(String author,
                     String title,
                     int total_quantity,
                     int quantity_available,
                     int id_book) {
        this.author = author;
        this.title = title;
        this.totalQuantity = total_quantity;
        this.quantityAvailable = quantity_available;
        this.idBook = id_book;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}
