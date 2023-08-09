package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.models.Book;
import ku.cs.models.BookList;
import ku.cs.services.BookListFileDatasource;
import ku.cs.services.Datasource;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class BooksController {
    @FXML private Label titleLabel;
    @FXML private Label authorLabel;
    @FXML private Label yearLabel;
    @FXML private Label statusLabel;
    @FXML private ImageView cover;
    @FXML private ListView<String> myListView;
    @FXML private TextField scoreField;
    @FXML private Label errorLabel;
    private Datasource<BookList> datasource;

    private BookList books;
    private Book selectedBook;

    @FXML
    public void initialize() {
//      HardcodeDatasource datasource = new HardcodeDatasource();
//      Datasource<BookList> datasource = new HardcodeDatasource();
        datasource = new BookListFileDatasource("data", "book-list.csv"); // folder, file
        books = datasource.readData();

        showList(books);

        myListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null) {
                selectedBook = null;
            } else {
                selectedBook = books.findByTitle(newValue);
                showBook(selectedBook);
            }
        });

        // Default book image
        Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/default.jpg")));
        cover.setImage(defaultImage);
    }

    private void showList(BookList books) {
        myListView.getItems().clear();
        myListView.getItems().addAll(books.getBookTitles());
    }

    private void showBook(Book book) {
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        yearLabel.setText(String.format("%d", book.getYear()));
        statusLabel.setText(book.getStatusText());

        Image image = loadBookImage(book);
        cover.setImage(image);
    }

    @FXML
    public void onGiveScore() {
        String scoreString = scoreField.getText().trim();
        if (scoreString.equals("")) {
            errorLabel.setText("Score is required");
            return;
        }
        try {
            int score = Integer.parseInt(scoreString);
            if (score < 0) {
                errorLabel.setText("Score must be a positive number");
                return;
            }
            errorLabel.setText("");
            selectedBook.giveScore(score); // Assuming you have a method in the Book class for giving scores
            scoreField.clear();
            datasource.writeData(books); // Write the updated data back to the file
            showBook(selectedBook);
        } catch (NumberFormatException e) {
            errorLabel.setText("Score must be a number");
        }
    }

    private Image loadBookImage(Book book) {
        String imagePath = String.format("/assets/%s.jpg", book.getTitle());
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
    }

    private ArrayList<Book> getBorrowedBooks() {
        ArrayList<Book> borrowedBooks = new ArrayList<>();
        for (Book book : books.getBooks()) {
            if (!book.isAvailable()) {
                borrowedBooks.add(book);
            }
        }
        return borrowedBooks;
    }

    @FXML
    private void onBorrowButton() {
        if (selectedBook != null && selectedBook.isAvailable()) {
            selectedBook.borrowBook();
            showBook(selectedBook);
        }
    }

    @FXML
    private void onReturnButton() {
        if (selectedBook != null && !selectedBook.isAvailable()) {
            selectedBook.returnBook();
            showBook(selectedBook);
        }
    }

    @FXML
    private void onButtonClick() throws IOException {
        FXRouter.goTo("hello");
    }

    @FXML
    private void onHistoryButton() throws IOException {
        ArrayList<Book> borrowedBooks = getBorrowedBooks();
        FXRouter.goTo("history", borrowedBooks);
    }
}
