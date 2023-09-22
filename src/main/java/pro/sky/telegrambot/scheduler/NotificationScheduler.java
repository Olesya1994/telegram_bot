package pro.sky.telegrambot.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.NotificationRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Service

public class NotificationScheduler {

    private final TelegramBot bot;

    private final NotificationRepository repository;
    private Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);

    public NotificationScheduler(TelegramBot bot, NotificationRepository repository) {
        this.bot = bot;
        this.repository = repository;
    }

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedDelay = 1)
    private void notifyTask() {
        repository.findAllByDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .forEach(notificationTask -> {
                    bot.execute(new SendMessage(notificationTask.getChatId(), notificationTask.getText()));
                    logger.info("Задача отправлена"+notificationTask);
                    repository.delete(notificationTask);
                });

    }


}
