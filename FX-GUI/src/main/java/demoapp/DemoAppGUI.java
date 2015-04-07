package demoapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@SpringBootApplication
public class DemoAppGUI extends Application {
    public final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("resources");

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            URL mainWindowFxml = getClass().getResource("/fxml/tabpane.fxml");
            Parent root = FXMLLoader.load(mainWindowFxml, RESOURCE_BUNDLE);

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle(RESOURCE_BUNDLE.getString("app.name"));
            primaryStage.setScene(scene);
            primaryStage.show();

            primaryStage.setOnCloseRequest((windowEvent) -> {
                System.exit(0);
            });

        } catch (IOException e) {
            log.error("Can't load JAVA FX Gui." , e);
        }
    }


    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication(DemoAppGUI.class);
        springApplication.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                if (event instanceof EmbeddedServletContainerInitializedEvent) {
                    launch(DemoAppGUI.class);
                }
            }
        });
        springApplication.run(args);
    }
}
