package demoapp.fx.student.controller;


import demoapp.ServiceLocator;
import demoapp.location.model.Location;
import demoapp.student.model.Student;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.rapidpm.modul.javafx.dialog.Dialog;

import java.util.concurrent.Executors;

public class StudentFormController {
    @FXML private TextField txtName;
    @FXML private ComboBox<Location> cmbCity;
    @FXML private DatePicker txtBirthday;

    public void initialize() {
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'student-form.fxml'.";
        assert cmbCity != null : "fx:id=\"cmbCity\" was not injected: check your FXML file 'student-form.fxml'.";
        assert txtBirthday != null : "fx:id=\"txtBirthday\" was not injected: check your FXML file 'student-form.fxml'.";

        cmbCity.setItems(FXCollections.observableArrayList(ServiceLocator.getLocationService().getLocations()));
    }

    @FXML
    protected void saveForm(ActionEvent event) {
        Executors.newSingleThreadExecutor().submit(new SaveStudentTask());

        closeForm(event);
    }

    @FXML
    protected void closeForm(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getTarget()).getParent().getScene().getWindow();
        window.getOwner().getScene().getRoot().setEffect(null);
        window.close();
    }

    private class SaveStudentTask extends Task<Student> {

        @Override
        protected Student call() throws Exception {
            Student student = new Student(null, txtName.getText(), cmbCity.getValue(), txtBirthday.getValue());
            return ServiceLocator.getStudentService().addStudent(student);
        }

        @Override
        protected void failed() {
            Platform.runLater(() -> Dialog.showError("Error", "Failed to add Student!"));
        }
    }
}
