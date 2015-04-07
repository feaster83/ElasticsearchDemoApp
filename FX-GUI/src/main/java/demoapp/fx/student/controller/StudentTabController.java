package demoapp.fx.student.controller;


import demoapp.DemoAppGUI;
import demoapp.ServiceLocator;
import demoapp.fx.student.FXStudent;
import demoapp.fx.student.FXStudentMapper;
import demoapp.student.model.Student;
import demoapp.student.service.StudentServiceEventListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class StudentTabController {
    private ObservableList<FXStudent> students;

    @FXML private TableView<FXStudent> studentsTable;
    @FXML private TableColumn<FXStudent, String> nameColumn;
    @FXML private TableColumn<FXStudent, String> cityColumn;
    @FXML private TableColumn<FXStudent, String> birthdayColumn;

    public void initialize() {
        assert studentsTable != null : "fx:id=\"studentsTable\" was not injected: check your FXML file 'tab-students.fxml'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'tab-students.fxml'.";
        assert cityColumn != null : "fx:id=\"cityColumn\" was not injected: check your FXML file 'tab-students.fxml'.";
        assert birthdayColumn != null : "fx:id=\"birthdayColumn\" was not injected: check your FXML file 'tab-students.fxml'.";

        nameColumn.setCellValueFactory(new PropertyValueFactory<FXStudent, String>("name"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<FXStudent, String>("city"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<FXStudent, String>("birthday"));

        List<Student> students = ServiceLocator.getStudentService().getStudents();
        List<FXStudent> fxStudentList = students.stream().map(FXStudentMapper::getStudent).collect(Collectors.toList());

        this.students = FXCollections.observableArrayList(fxStudentList);
        studentsTable.setItems(this.students);

        ServiceLocator.getStudentService().addStudentEventListener(new StudentEventListener());
    }

    @FXML
    protected void openAddNewStudentDialog(ActionEvent event) throws IOException {
        Stage dialogStage = new Stage();
        URL dialogFxml = getClass().getResource("/fxml/student-form.fxml");
        Parent root = FXMLLoader.load(dialogFxml, DemoAppGUI.RESOURCE_BUNDLE);

        Scene scene = new Scene(root, 300, 150);
        dialogStage.setResizable(false);

        dialogStage.setTitle(DemoAppGUI.RESOURCE_BUNDLE.getString("dialog.addstudent.title"));
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        Scene primaryscene = ((Node)event.getSource()).getScene();
        dialogStage.initOwner(primaryscene.getWindow());
        primaryscene.getRoot().setEffect(new BoxBlur());

        dialogStage.show();
    }

    private class StudentEventListener implements StudentServiceEventListener {

        @Override
        public void studentAdded(Student student) {
            FXStudent fxStudent = FXStudentMapper.getStudent(student);

            Platform.runLater(() -> {
                students.add(fxStudent);
            });
        }

        @Override
        public void studentRemoved(Student student) {
            FXStudent fxStudent = FXStudentMapper.getStudent(student);

            Platform.runLater(() -> {
                students.remove(fxStudent);
            });
        }

        @Override
        public void studentUpdated(Student student) {
            FXStudent fxStudent = FXStudentMapper.getStudent(student);

            Platform.runLater(() -> {
                students.remove(fxStudent);
                students.add(fxStudent);
            });
        }
    }
}
