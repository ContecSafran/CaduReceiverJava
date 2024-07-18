package kr.contec.fromto.cam.safran.context;

import kr.contec.fromto.cam.safran.HdrCaduReceiverCam;
import kr.contec.fromto.cam.safran.HdrCaduReceiverVersion;
import kr.contec.fromto.cam.safran.config.ApplicationConfig;
import kr.contec.fromto.framework.CamFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * 어플리케이션 라이프 사이클
 *
 * @author bskim
 */
@Component
@Profile("ops")
@AllArgsConstructor
@Slf4j
public class ApplicationLifecycle {
    private final Path writePath;

    private final ApplicationConfig config;
    /**
     * 시작
     */
    @EventListener(ContextRefreshedEvent.class)
    public void start() {

        HdrCaduReceiverCam geoSyncKubucCam = (HdrCaduReceiverCam) CamFactory.get("key", "name", HdrCaduReceiverVersion.SATCORE, config.getIpAddress());
        geoSyncKubucCam.setConfig(config,writePath);
    }

}
