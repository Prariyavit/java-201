package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import ku.cs.models.Book;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.util.ArrayList;

public class HistoryController {
    @FXML
    private ListView<Book> historyListView;

    @FXML
    private void initialize() {
        // Retrieve the data from FXRouter using getData()
        ArrayList<Book> borrowedBooks = (ArrayList<Book>) FXRouter.getData();
        if (borrowedBooks != null) {
            historyListView.getItems().addAll(borrowedBooks);
        }
    }

    @FXML
    private void onBackButton() throws IOException {
        FXRouter.goTo("book");
    }
}
