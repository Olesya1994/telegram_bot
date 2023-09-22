package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ScheduleCommand implements Command {
    private final TelegramBot bot;
    private final NotificationRepository repository;
    private final Logger logger = LoggerFactory.getLogger(ScheduleCommand.class);

    public ScheduleCommand(TelegramBot bot, NotificationRepository repository) {
        this.bot = bot;
        this.repository = repository;
    }

    @Override
    public boolean isPossibleToConvert(Update update) {
        Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
        Matcher matcher = pattern.matcher(update.message().text());
        boolean response = matcher.matches();
        if (!response) {
            SendMessage message = new SendMessage(update.message().chat().id(), "Формат команды не верен");
            bot.execute(message);
        }
        return response;
    }

    @Override
    public void sendRespond(Update update) {
        Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
        Matcher matcher = pattern.matcher(update.message().text());
        long charId = update.message().chat().id();
        if (matcher.matches()) {
            String date = matcher.group(1);
            String text = matcher.group(3);
            LocalDateTime dateTime = null;
            try {
                dateTime = LocalDateTime.
                        parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            } catch (DateTimeParseException e) {
                logger.info("Can not parse to data" + date);
                SendMessage message = new SendMessage(update.message().chat().id(), "Формат даты не верен" + date);
            }
            repository.save(new NotificationTask(text, charId, dateTime));
            SendMessage message = new SendMessage(update.message().chat().id(), "записано сообытие" + date + " " + text);
            bot.execute(message);

        }
    }
}
