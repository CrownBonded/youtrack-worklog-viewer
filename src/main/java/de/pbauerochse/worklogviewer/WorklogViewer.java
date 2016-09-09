package de.pbauerochse.worklogviewer;

import de.pbauerochse.worklogviewer.fx.MainViewController;
import de.pbauerochse.worklogviewer.settings.Settings;
import de.pbauerochse.worklogviewer.settings.WindowSettings;
import de.pbauerochse.worklogviewer.util.ExceptionUtil;
import de.pbauerochse.worklogviewer.util.FormattingUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 * @author Patrick Bauerochse
 * @since 01.04.15
 */
public class WorklogViewer extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorklogViewer.class);

    private static WorklogViewer instance;

    public static WorklogViewer getInstance() {
        if (instance == null) {
            throw ExceptionUtil.getIllegalStateException("exceptions.main.instance");
        }
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Stage primaryStage;

    @Override
    public void stop() throws Exception {
        Settings.save();
        MainViewController.EXECUTOR.shutdownNow();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.primaryStage = primaryStage;

        Locale locale = Locale.getDefault();
        Charset charset = Charset.defaultCharset();

        LOGGER.info("Default Locale: {}", locale);
        LOGGER.info("Default Charset: {}", charset);

        WindowSettings settings = Settings.get().getWindowSettings();

        FXMLLoader loader = new FXMLLoader(Charset.forName("utf-8"));
        loader.setResources(FormattingUtil.RESOURCE_BUNDLE);

        Parent root = loader.load(WorklogViewer.class.getResource("/fx/views/main.fxml"), FormattingUtil.RESOURCE_BUNDLE);
        Scene mainScene = new Scene(root, settings.getWidth(), settings.getHeight());
        mainScene.getStylesheets().add("/fx/css/main.css");

        primaryStage.setTitle("YouTrack Worklog Viewer " + FormattingUtil.getFormatted("release.version"));
        primaryStage.setScene(mainScene);
        primaryStage.setX(settings.getPosX());
        primaryStage.setY(settings.getPosY());
        primaryStage.show();

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> settings.setWidth(newValue.intValue()));
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> settings.setHeight(newValue.intValue()));
        primaryStage.xProperty().addListener((observable, oldValue, newValue) -> settings.setPosX(newValue.intValue()));
        primaryStage.yProperty().addListener((observable, oldValue, newValue) -> settings.setPosY(newValue.intValue()));
    }

    public void requestShutdown() {
        LOGGER.debug("Shutdown requested");
        primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
