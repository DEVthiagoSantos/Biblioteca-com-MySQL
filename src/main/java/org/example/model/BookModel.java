package org.example.model;

public class BookModel {

    private int id_book;
    private String author;
    private String title;
    private int total_quantity;
    private int quantity_available;

    public BookModel() {}

    public BookModel(String author,
                     String title,
                     int total_quantity,
                     int quantity_available) {
        this.author = author;
        this.title = title;
        this.total_quantity = total_quantity;
        this.quantity_available = quantity_available;
    }

    public BookModel(String author,
                     String title,
                     int total_quantity,
                     int quantity_available,
                     int id_book) {
        this.author = author;
        this.title = title;
        this.total_quantity = total_quantity;
        this.quantity_available = quantity_available;
        this.id_book = id_book;
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
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

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public int getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(int quantity_available) {
        this.quantity_available = quantity_available;
    }
}
