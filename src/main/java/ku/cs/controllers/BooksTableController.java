package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.Book;
import ku.cs.models.BookList;
import ku.cs.services.Datasource;
import ku.cs.services.BookListFileDatasource;

public class BooksTableController {

    @FXML private TableView<Book> booksTableView;

    private BookList bookList;

    private Datasource<BookList> datasource;

    @FXML
    public void initialize() {
        datasource = new BookListFileDatasource("data", "book-list.csv");
        bookList = datasource.readData();
        showTable(bookList);
    }

    private void showTable(BookList bookList) {
        // กำหนด column ให้มี title ว่า ID และใช้ค่าจาก attribute id ของ object Student
        TableColumn<Book, String> idColumn = new TableColumn<>("Book ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        // กำหนด column ให้มี title ว่า Name และใช้ค่าจาก attribute name ของ object Student
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // กำหนด column ให้มี title ว่า Score และใช้ค่าจาก attribute score ของ object Student
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Book, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        // ล้าง column เดิมทั้งหมดที่มีอยู่ใน table แล้วเพิ่ม column ใหม่
        booksTableView.getColumns().clear();

        booksTableView.getColumns().add(idColumn);
        booksTableView.getColumns().add(titleColumn);
        booksTableView.getColumns().add(authorColumn);
        booksTableView.getColumns().add(yearColumn);
        booksTableView.getColumns().add(scoreColumn);

        booksTableView.getItems().clear();

        // ใส่ข้อมูล Student ทั้งหมดจาก studentList ไปแสดงใน TableView
        for (Book book: bookList.getBooks()) {
            booksTableView.getItems().add(book);
        }
    }
}