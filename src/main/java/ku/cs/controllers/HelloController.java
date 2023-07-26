package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class HelloController {

    @FXML
    private ImageView logo;

    @FXML
    public void initialize() {
        Image image = new Image(getClass().getResourceAsStream("/assets/kirby.png"));
        logo.setImage(image);
    }


    @FXML
    private void onBackButton() throws IOException {
        FXRouter.goTo("book");
    }
}