package kr.contec.fromto.cam.safran;

import kr.contec.fromto.framework.CamFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaduReceiver {
    public static void main(String[] args) {

        SpringApplication.run(CaduReceiver.class, args);
        //HdrCaduReceiverCam hdrCaduReceiverCam = (HdrCaduReceiverCam) CamFactory.get("key", "name", HdrCaduReceiverVersion.SATCORE, "127.0.0.1");
    }
}
