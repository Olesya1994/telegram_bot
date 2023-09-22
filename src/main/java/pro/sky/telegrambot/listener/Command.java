package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.model.Update;

public interface Command {
    public boolean isPossibleToConvert(Update update);
    public void sendRespond(Update update);
}
