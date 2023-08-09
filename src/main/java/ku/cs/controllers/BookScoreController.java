package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.models.Book;
import ku.cs.models.BookList;
import ku.cs.services.Datasource;
import ku.cs.services.FXRouter;
import ku.cs.services.BookListFileDatasource;

import java.io.IOException;

public class BookScoreController {
    @FXML private Label titleLabel;
    @FXML private Label idLabel;
    @FXML private Label scoreLabel;
    @FXML private Label errorLabel;
    @FXML private TextField scoreTextField;

    private Datasource<BookList> datasource;
    private BookList bookList;
    private Book book;

    @FXML
    public void initialize() {
        datasource = new BookListFileDatasource("data", "book-list.csv");
        bookList = datasource.readData();

        // รับข้อมูล studentId จากหน้าอื่น ผ่าน method FXRouter.getData()
        // โดยจำเป็นต้อง casting data type ให้ตรงกับหน้าที่ส่งข้อมูล
        String studentId = (String) FXRouter.getData();
        book = bookList.findByTitle(studentId);

        showStudent(book);

        errorLabel.setText("");
    }

    private void showStudent(Book book) {
        titleLabel.setText(book.getTitle());
        idLabel.setText(book.getId());
        scoreLabel.setText("" + book.getScore());
    }

    @FXML
    public void handleGiveScoreButton() {
        String scoreString = scoreTextField.getText().trim();
        if (scoreString.equals("")) {
            errorLabel.setText("score is required");
            return;
        }
        try {
            int score = Integer.parseInt(scoreString);
            if (score < 0) {
                errorLabel.setText("score must be positive number");
                return;
            }
            errorLabel.setText("");
            bookList.giveScoreToId(book.getId(), score);
            scoreTextField.clear();
            datasource.writeData(bookList);
            showStudent(book);
        } catch (NumberFormatException e) {
            errorLabel.setText("score must be number");
        }
    }

    @FXML
    public void handleBackToStudentsTableButton() {
        try {
            FXRouter.goTo("students-table");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}