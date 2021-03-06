package com.example.intergration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

@Configuration
public class FileWriterIntegrationConfig {
//    @Bean
//    @Transformer(inputChannel = "textInChannel",
//            outputChannel = "fileWriterChannel")
//    public GenericTransformer<String, String> upperCaseTransformer(){
//        return text -> text.toUpperCase(Locale.ROOT);
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "filwWriterChannel")
//    public FileWritingMessageHandler fileWriter(){
//        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("/tmp/sia5/files"));
//        handler.setExpectReply(false);
//        handler.setFileExistsMode(FileExistsMode.APPEND);
//        handler.setAppendNewLine(true);
//        return handler;
//    }

    @Bean
    public IntegrationFlow fileWriterFlow(){
        return IntegrationFlows.from(MessageChannels.direct("textInChannel"))
                .<String, String>transform(String::toUpperCase)
                .handle(Files
                        .outboundAdapter(new File("/tmp/sia5/files"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true)
                    )
                .get();
    }
}
