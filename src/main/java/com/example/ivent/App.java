// ETHEL AKOSUA AKRASI - 10917233

package com.example.ivent;

import com.example.ivent.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage){
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
