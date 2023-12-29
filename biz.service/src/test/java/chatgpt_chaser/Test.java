package chatgpt_chaser;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import zero.transform_chaser.openai.Proxys;
import zero.transform_chaser.openai.manager.ChatGPT;
import zero.transform_chaser.openai.model.ChatCompletion;
import zero.transform_chaser.openai.model.ChatCompletionResponse;
import zero.transform_chaser.openai.model.Message;
import zero.transform_chaser.openai.model.enu.ModelEnum;

import java.net.Proxy;
import java.util.Arrays;


@Slf4j
public class Test {
    public static void main(String[] args) {

        System.out.println("test");
    }

    private ChatGPT chatGPT;

    @Before
    public void before() {
        Proxy proxy = Proxys.http("127.0.0.1", 1080);

        chatGPT = ChatGPT.builder()
                .apiKey("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")
                .timeout(900)
                .proxy(proxy)
                .apiHost("https://api.openai.com/") //代理地址
                .build()
                .init();

//        CreditGrantsResponse response = chatGPT.creditGrants();
//        log.info("余额：{}", response.getTotalAvailable());
    }

    @org.junit.Test
    public void chat() {
        Message system = Message.ofSystem("你现在是一个诗人，专门写七言绝句");
        Message message = Message.of("写一段七言绝句诗，题目是：火锅！");

        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ModelEnum.GPT_3_5_TURBO.getName())
                .messages(Arrays.asList(system, message))
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        System.out.println(res);
    }

    @org.junit.Test
    public void chatmsg() {
        String res = chatGPT.chat("写一段七言绝句诗，题目是：火锅！");
        System.out.println(res);
    }

    /**
     * 测试tokens数量计算
     */
    @org.junit.Test
    public void tokens() {
        Message system = Message.ofSystem("你现在是一个诗人，专门写七言绝句");
        Message message = Message.of("写一段七言绝句诗，题目是：火锅！");

        ChatCompletion chatCompletion1 = ChatCompletion.builder()
                .model(ModelEnum.GPT_3_5_TURBO.getName())
                .messages(Arrays.asList(system, message))
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletion chatCompletion2 = ChatCompletion.builder()
                .model(ModelEnum.GPT_4.getName())
                .messages(Arrays.asList(system, message))
                .maxTokens(3000)
                .temperature(0.9)
                .build();

        log.info("{} tokens: {}", chatCompletion1.getModel(), chatCompletion1.countTokens());
        log.info("{} tokens: {}", chatCompletion2.getModel(), chatCompletion2.countTokens());
    }

}
