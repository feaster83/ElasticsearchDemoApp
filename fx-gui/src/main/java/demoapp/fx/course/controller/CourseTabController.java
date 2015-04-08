package demoapp.fx.course.controller;


import demoapp.DemoAppGUI;
import demoapp.ServiceLocator;
import demoapp.course.model.Course;
import demoapp.course.service.CourseServiceEventListener;
import demoapp.fx.course.FXCourse;
import demoapp.fx.course.FXCourseMapper;
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
public class CourseTabController {
    private ObservableList<FXCourse> courses;

    @FXML private TableView<FXCourse> coursesTable;
    @FXML private TableColumn<FXCourse, String> nameColumn;
    @FXML private TableColumn<FXCourse, String> priceColumn;

    public void initialize() {
        assert coursesTable != null : "fx:id=\"coursesTable\" was not injected: check your FXML file 'tab-courses.fxml'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'tab-courses.fxml'.";
        assert priceColumn != null : "fx:id=\"priceColumn\" was not injected: check your FXML file 'tab-courses.fxml'.";

        nameColumn.setCellValueFactory(new PropertyValueFactory<FXCourse, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<FXCourse, String>("price"));

        List<Course> courses = ServiceLocator.getCourseService().getCourses();
        List<FXCourse> fxCourseList = courses.stream().map(FXCourseMapper::getCourse).collect(Collectors.toList());

        this.courses = FXCollections.observableArrayList(fxCourseList);
        coursesTable.setItems(this.courses);

        ServiceLocator.getCourseService().addCourseEventListener(new CourseEventListener());
    }

    @FXML
    protected void openAddNewCourseDialog(ActionEvent event) throws IOException {
        Stage dialogStage = new Stage();
        URL dialogFxml = getClass().getResource("/fxml/course-form.fxml");
        Parent root = FXMLLoader.load(dialogFxml, DemoAppGUI.RESOURCE_BUNDLE);

        Scene scene = new Scene(root, 300, 120);
        dialogStage.setResizable(false);

        dialogStage.setTitle(DemoAppGUI.RESOURCE_BUNDLE.getString("dialog.addcourse.title"));
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        Scene primaryscene = ((Node)event.getSource()).getScene();
        dialogStage.initOwner(primaryscene.getWindow());
        primaryscene.getRoot().setEffect(new BoxBlur());

        dialogStage.show();
    }

    private class CourseEventListener implements CourseServiceEventListener {

        @Override
        public void courseAdded(Course course) {
            FXCourse fxCourse = FXCourseMapper.getCourse(course);

            Platform.runLater(() -> {
                courses.add(fxCourse);
            });
        }

        @Override
        public void courseRemoved(Course course) {
            FXCourse fxCourse = FXCourseMapper.getCourse(course);

            Platform.runLater(() -> {
                courses.remove(fxCourse);
            });
        }

        @Override
        public void courseUpdated(Course course) {
            FXCourse fxCourse = FXCourseMapper.getCourse(course);

            Platform.runLater(() -> {
                courses.remove(fxCourse);
                courses.add(fxCourse);
            });
        }
    }
}
