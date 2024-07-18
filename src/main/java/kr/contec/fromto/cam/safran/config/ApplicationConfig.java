package kr.contec.fromto.cam.safran.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 경로 설정
 *
 * @author bskim
 */
@Getter
@Component
public class ApplicationConfig {
    /**
     * 인풋 패스 설정
     *
     * @param input 인풋 경로
     * @return 인풋 패스
     */

    @Bean
    public Path writePath(@Value("${config.directory.write}") String input) throws IOException {
        Path path = Paths.get(input);
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        return path;
    }

    /**
     * 원본 저장 확장자
     *
     */
    @Value("${config.write.extension}")
    private String writeExtension;

    /**
     * cadu 저장 확장자
     *
     */
    @Value("${config.cadu.extension}")
    private String caduExtension;


    /**
     * cadu 저장 확장자
     *
     */
    @Value("${config.cadu.ip}")
    private String ipAddress;


    @Value("${config.use.dpu1}")
    private boolean useDpu1;

    @Value("${config.use.dpu2}")
    private boolean useDpu2;

    @Value("${config.use.dpu3}")
    private boolean useDpu3;

    @Value("${config.use.dpu4}")
    private boolean useDpu4;
}
