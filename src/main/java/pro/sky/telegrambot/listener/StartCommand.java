package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Service;

@Service
public class StartCommand implements Command {
    private final TelegramBot bot;

    public StartCommand(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public boolean isPossibleToConvert(Update update) {
        return update.message().text().equals("/start");
    }

    @Override
    public void sendRespond(Update update) {
        SendMessage message = new SendMessage(update.message().chat().id(), "Добро пожаловать");
        SendResponse response = bot.execute(message);
    }
}
