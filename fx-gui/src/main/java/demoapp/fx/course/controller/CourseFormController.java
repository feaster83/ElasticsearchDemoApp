package demoapp.fx.course.controller;


import demoapp.ServiceLocator;
import demoapp.course.model.Course;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.rapidpm.modul.javafx.dialog.Dialog;

import java.util.concurrent.Executors;

public class CourseFormController {
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;

    public void initialize() {
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'course-form.fxml'.";
        assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'course-form.fxml'.";
    }

    @FXML
    protected void saveForm(ActionEvent event) {
        Executors.newSingleThreadExecutor().submit(new SaveCourseTask());

        closeForm(event);
    }

    @FXML
    protected void closeForm(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getTarget()).getParent().getScene().getWindow();
        window.getOwner().getScene().getRoot().setEffect(null);
        window.close();
    }

    private class SaveCourseTask extends Task<Course> {

        @Override
        protected Course call() throws Exception {
            Course course = new Course(null, txtName.getText(), Float.valueOf(txtPrice.getText()));
            return ServiceLocator.getCourseService().addCourse(course);
        }

        @Override
        protected void failed() {
            Platform.runLater(() -> Dialog.showError("Error", "Failed to add Course!"));
        }
    }
}
