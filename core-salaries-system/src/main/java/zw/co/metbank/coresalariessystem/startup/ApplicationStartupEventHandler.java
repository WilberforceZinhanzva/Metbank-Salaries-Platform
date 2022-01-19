package zw.co.metbank.coresalariessystem.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupEventHandler implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private StartupService startupService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        startupService.init();
    }
}
