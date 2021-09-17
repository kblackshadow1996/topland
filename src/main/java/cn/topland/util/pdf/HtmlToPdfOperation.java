package cn.topland.util.pdf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * html转pdf
 */
@Slf4j
@Setter
@Getter
@AllArgsConstructor
public class HtmlToPdfOperation {

    private HtmlToPdfParams params;

    public void apply(String src, String dest) {

        String command = getCommand(src, dest, params.buildParams());
        executeCommand(command);
    }

    private static String getCommand(String url, String outputPath, String params) {

        StringBuilder command = new StringBuilder("wkhtmltopdf ")
                .append(params)
                .append(url).append(" ")
                .append(outputPath);
        return command.toString();
    }

    private static void executeCommand(String command) {

        CommandLine commandLine = CommandLine.parse(command);

        OutputStream outputStream = new ByteArrayOutputStream();
        ExecuteStreamHandler streamHandler = new PumpStreamHandler(outputStream);

        Executor exec = new DefaultExecutor();
        exec.setExitValues(null);
        exec.setStreamHandler(streamHandler);
        try {

            exec.execute(commandLine);
        } catch (IOException e) {

            log.error("wkhtmltopdf error", e);
        }
    }
}