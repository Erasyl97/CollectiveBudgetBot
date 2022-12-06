package com.tou.io.CollectiveBudgetBot.service;

import com.tou.io.CollectiveBudgetBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class CollectiveBudgetBot extends TelegramLongPollingBot {

    final BotConfig config;

    public CollectiveBudgetBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().hasText() ? update.getMessage().getText() : "";
        String userName = update.getMessage().getChat().getFirstName();
//        update.getMessage().isGroupMessage();

        switch (message) {
            case "/start":
                startMessageReceived(chatId, userName);
                break;
            default:
                sendAnswer(chatId,"Sorry, unrecognized command message!");
        }
    }

    private void startMessageReceived(Long chatId, String userName) {

        String answer = "Hi, " + userName + ", nice to meet you!";

        sendAnswer(chatId, answer);
    }

    private void sendAnswer(Long chatId, String answer) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(answer);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
