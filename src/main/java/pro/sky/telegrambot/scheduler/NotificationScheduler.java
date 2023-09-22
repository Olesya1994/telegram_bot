package pro.sky.telegrambot.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.NotificationRepository;

import java.util.concurrent.TimeUnit;

@Service

public class NotificationScheduler {
    @Autowired
    private final TelegramBot bot;
    @Autowired
    private final NotificationRepository repository;
    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedDelay = 1)
    private void notifyTask(){
        repository.findAllByDateTime()

    }


}
