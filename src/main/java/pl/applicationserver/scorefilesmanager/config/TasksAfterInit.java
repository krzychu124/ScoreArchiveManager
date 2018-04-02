package pl.applicationserver.scorefilesmanager.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.applicationserver.scorefilesmanager.scheduler.PdfThumbnailGenerator;

@Component
public class TasksAfterInit implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        applicationReadyEvent.getApplicationContext().getBean(PdfThumbnailGenerator.class).findFilesToGenerateThumbs();
    }
}