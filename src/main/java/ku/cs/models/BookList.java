package ku.cs.models;

import java.util.ArrayList;
import java.util.List;

public class BookList {
    private ArrayList<Book> books;

    public BookList() {
        books = new ArrayList<>();
    }

    public void addBook(String id, String title, String author, int year, int score) {
        if (!id.isEmpty() && !title.isEmpty() && !author.isEmpty() && year >= 0 && score >= 0) {
            books.add(new Book(id.trim(), title.trim(), author.trim(), year, score));
        }
    }

    public Book findByTitle(String title) {
        for (Book book : books) {
            if (book.isTitle(title)) {
                return book;
            }
        }
        return null;
    }

    public Book findById(String id) {
        for (Book book : books) {
            if (book.isId(id)) {
                return book;
            }
        }
        return null;
    }

    public void giveScoreToId(String id, int score) {
        for (Book book : books) {
            if (book.isId(id)) {
                book.setScore(score);
            }
        }
    }

    public List<String> getBookTitles() {
        List<String> titles = new ArrayList<>();
        for (Book book : books) {
            titles.add(book.getTitle());
        }
        return titles;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
