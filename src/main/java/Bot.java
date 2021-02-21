import lombok.Getter;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

@Getter

public class Bot extends TelegramLongPollingBot {
    private int randomNumber;
    private int count;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

    }
    
    public void onUpdateReceived(Update update) {
        //входящее смс для бота
        String message = update.getMessage().getText();
        //исходящее смс от бота
        String message1 = update.getMessage().getChatId().toString();

        IrregularVerbs irregularVerbs = new IrregularVerbs();
        List<ModelVerbs> modelVerbsList = irregularVerbs.readIrrVerbs();
        // int count = 0;
        int i = 0;
        if (message != null) {
            if (message.equals("start")) {
                sendMsg(message1, "поехали ёпта");
                //  checkWords();
                i = (int) (Math.random() * modelVerbsList.size());
                randomNumber = i;
                String str = modelVerbsList.get(getRandomNumber()).getRussianForm();
                sendMsg(update.getMessage().getChatId().toString(), "Глагол: " + str);
                sendMsg(update.getMessage().getChatId().toString(), "Введи Infinitive");

            } else if (message.equals("help")) {

                sendMsg(update.getMessage().getChatId().toString(), "тут будет когда-то помощь");

            } else if (message.equals("stop")) {

                sendMsg(update.getMessage().getChatId().toString(), "может быть сделаем остановочку");

            } else if (message.equalsIgnoreCase(modelVerbsList.get(getRandomNumber()).getFirstForm())) {

                sendMsg(update.getMessage().getChatId().toString(), "Красавчик");
                sendMsg(update.getMessage().getChatId().toString(), "Введи Past Simple");
                count++;

            } else if (message.equalsIgnoreCase(modelVerbsList.get(getRandomNumber()).getSecondForm()) && getCount() == 1) {

                sendMsg(update.getMessage().getChatId().toString(), "Красавчик");
                sendMsg(update.getMessage().getChatId().toString(), "Введи Past Participle");
                count++;

            } else if (message.equalsIgnoreCase(modelVerbsList.get(getRandomNumber()).getThirdForm()) && getCount() == 2) {
                sendMsg(update.getMessage().getChatId().toString(), "Красавчик");
                i = (int) (Math.random() * modelVerbsList.size());
                randomNumber = i;
                String str = modelVerbsList.get(getRandomNumber()).getRussianForm();
                sendMsg(update.getMessage().getChatId().toString(), "Глагол: " + str);
                sendMsg(update.getMessage().getChatId().toString(), "Введи Infinitive");
                count = 0;

            } else {

                sendMsg(update.getMessage().getChatId().toString(), "НУ ТАКОЕ!");

            }
        }
        // sendMsg(update.getMessage().getChatId().toString(), message);

//        Message message = update.getMessage();
//        if (message != null && message.hasText()) {
//
//            //int i = (int) (Math.random() * modelVerbsList.size());
//            // sendMsg(message, modelVerbsList.get(i).getRussianForm());
//            switch (message.getText()) {
//                case "/help":
//                    sendMsg(message, "Чем могу помочь?");
//                    break;
//                case "/start":
//                    sendMsg(message, "Привет, " + message.getChat().getFirstName() + "! " + "В этом боте мы будем изучать неправильные глаголы.");
//                    //  sendMsg(message, modelVerbsList.get(0).getRussianForm());
//                    sendMsg(message, working(message, update));
//
////                    working(message, update);
//
//                    break;
////                default:
////                    working(message, update);
////                    break;
//            }
//
//        }

    }


    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            setButtons(sendMessage);
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

//    public void sendMsg(Message message, String str) {
//        SendMessage sendMessage = new SendMessage();
//        //sendMessage.enableMarkdown(true);
//        sendMessage.setChatId(message.getChatId().toString());
//        //sendMessage.setReplyToMessageId(message.getMessageId());
//        sendMessage.setText(str);
//
//        try {
//            // кнопка
//            setButtons(sendMessage);
//            //сообщение с ответом
//            sendMessage(sendMessage);
//
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

    public void setButtons(SendMessage sendMessage) {
        // создаём клавиатуру (инциализируем клаву)
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        // устанавливаем разметку для клавиатуры (связываем сообщение с клавиатурой)
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        // этот параметр нужен , чтобы показывать клавиатуру только определённым пользователям
        replyKeyboardMarkup.setSelective(true);

        // указывает подгонку клавиатуры под кол-во кнопок
        // (Указывает клиенту подогнать высоту клавиатуры под количество кнопок (сделать её меньше, если кнопок мало).)
        replyKeyboardMarkup.setResizeKeyboard(true);

        //указываем параметр скрывать клавиатуру после нажатия кнопки.
        // Указывает клиенту скрыть клавиатуру после использования (после нажатия на кнопку).
        // Её по-прежнему можно будет открыть через иконку в поле ввода сообщения. По умолчанию False, т.е. не скрывать клаву.
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // создаём кнопки
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("start"));
        keyboardFirstRow.add(new KeyboardButton("help"));
        keyboardFirstRow.add(new KeyboardButton("stop"));

        keyboardRowList.add(keyboardFirstRow);
        //устанавливаем этот список в клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboardRowList);


    }

    public String getBotUsername() {
        return "IrregularVerbsBot";
    }

    public String getBotToken() {
        return "1631543791:AAH0q6yJ74oLRRE_DjAol04XFAT9EX0iJK0";
    }
}
