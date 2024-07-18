package kr.contec.fromto.cam.safran;

import kr.contec.fromto.cam.safran.config.ApplicationConfig;
import kr.contec.fromto.cam.safran.dto.Modem;
import kr.contec.fromto.cam.safran.modem.ModemReceiver;
import kr.contec.fromto.framework.Cam;
import kr.contec.fromto.framework.domain.CamConfig;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * HDR CADU Receiver
 *
 * @author skjeong
 */
public class HdrCaduReceiverCam extends Cam<HdrCaduReceiverControl, HdrCaduReceiverStatus>{

    private final ModemReceiver [] modemReceiver = new ModemReceiver[4];
    Path writePath;
 //   private final TcpClient tcpClient;

    ApplicationConfig applicationConfig;
    /**
     * Constructor (생성자)
     *
     * @param config 설정
     */
    public void setConfig(ApplicationConfig config, Path writePath){
        this.writePath = writePath;
        this.applicationConfig = config;
    }
    public HdrCaduReceiverCam(CamConfig config) {
        super(config);
        CamConfig.Access access = accesses.get(0);
        //access.getPort();
        // TCP Client 생성
        /*
        tcpClient = new TcpClient(meta, accesses.get(0),
                () -> List.of(new LengthFieldBasedFrameDecoder(10000000,4, 4,-8,0),
                        new SafranByteToMessageDecoder(),
                        new SafranDecoder()),
                () -> List.of(new SafranEncoder()), this);*/
        LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.UTC);
        String timeStr = String.format("%s_%s",
                dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                dateTime.format(DateTimeFormatter.ofPattern("HHmmss")));
        String dir = String.format("%s\\%s_%d%s",
                writePath,
                timeStr,
                0,
                applicationConfig.getWriteExtension());
        boolean [] useDpu = {applicationConfig.isUseDpu1(),applicationConfig.isUseDpu2(),applicationConfig.isUseDpu3(),applicationConfig.isUseDpu4()};
        for(int i = 0; i < modemReceiver.length; i++){
            if(useDpu[i]) {
                modemReceiver[i] = ModemReceiver.builder()
                        .modem(Modem.builder()
                                .fullWriteFileName(
                                        String.format("%s\\%s_%d%s",
                                                writePath,
                                                timeStr,
                                                i + 1,
                                                applicationConfig.getWriteExtension())
                                )
                                .caduWriteFileName(
                                        String.format("%s\\%s_%d_cadu%s",
                                                writePath,
                                                timeStr,
                                                i + 1,
                                                applicationConfig.getCaduExtension())
                                )
                                .meta(meta)
                                .access(access)
                                .id(i)
                                .build())
                        .build();
                modemReceiver[i].init();
            }
        }
    }

    @Override
    public void connect() {
    }

    @Override
    public void command(HdrCaduReceiverControl control) {
        /*
        List<String> commandLists = control.sendCommands();

        for (String command : commandLists) {
            tcpClient.send(command, 100, TimeUnit.MILLISECONDS);
        }*/
    }

    @Override
    public void disconnect() {
    }

    @Override
    public void preDestroy() {
    }

}
