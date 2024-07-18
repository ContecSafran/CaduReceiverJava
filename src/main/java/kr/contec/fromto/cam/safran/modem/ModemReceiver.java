package kr.contec.fromto.cam.safran.modem;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import kr.contec.fromto.cam.safran.dto.Modem;
import kr.contec.fromto.cam.safran.inbound.SafranByteToMessageDecoder;
import kr.contec.fromto.cam.safran.inbound.SafranModemReceiveDecoder;
import kr.contec.fromto.cam.safran.outbound.SafranEncoder;
import kr.contec.fromto.cam.safran.util.ConverterUtils;
import kr.contec.fromto.framework.CamConnection;
import kr.contec.fromto.framework.client.tcp.TcpChannelHandler;
import kr.contec.fromto.framework.client.tcp.TcpClient;
import kr.contec.fromto.framework.client.tcp.TcpConnectionListenable;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Getter
@Builder
@Slf4j
public class ModemReceiver  implements TcpConnectionListenable {
    private TcpClient tcpClient;
    Modem modem;
    FileOutputStream fileOutputStream = null;
    FileOutputStream caduOutputStream = null;
    final int hdrHeaderSize = 76;
    byte [] headerBuffer = null;
    public void init(){

        tcpClient = new TcpClient(modem.getMeta(), modem.getAccess(),
                () -> List.of(new LengthFieldBasedFrameDecoder(10000000,4, 4,-8,0),
                        new SafranByteToMessageDecoder(),
                        new SafranModemReceiveDecoder(this)),
                () -> List.of(new SafranEncoder()), this);
        headerBuffer = new byte[hdrHeaderSize];
        CompletableFuture.runAsync(() -> {
        }, CompletableFuture.delayedExecutor(100, TimeUnit.MILLISECONDS)).join();
        tcpClient.connect();
    }
    public boolean verification(byte [] data){

        int headerMark = ConverterUtils.toInt(data, 0);
        int tailMark = ConverterUtils.toInt(data, data.length - 4);
        int receiveSize = ConverterUtils.toInt(data,4);
        return (headerMark == 1234567890) && (tailMark == -1234567890) && (receiveSize == data.length) && (data.length > 56);
    }
    public void write(byte[] data) {
        try {
            if(Objects.isNull(fileOutputStream)) {
                fileOutputStream = new FileOutputStream(modem.getFullWriteFileName());
            }
            if(Objects.isNull(caduOutputStream)) {
                caduOutputStream = new FileOutputStream(modem.getCaduWriteFileName());
            }
            fileOutputStream.write(data);
            fileOutputStream.flush();
            if(verification(data)) {
                int frameSize = ConverterUtils.toInt(data, 36);
                int frameCount = ConverterUtils.toInt(data, 52);
                int tmBlockSize = ConverterUtils.toInt(data, 48) * 8;
                int writeOffset = hdrHeaderSize;
                for (int i = 0; i < frameCount; i++) {
                    caduOutputStream.write(data, writeOffset, frameSize);
                    writeOffset += tmBlockSize;
                }
                caduOutputStream.flush();
            }{
                log.error("수신 받은 데이터가 사프란 구조가 아닙니다");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    @Override
    public void channelActive(CamConnection type, TcpChannelHandler channel) {
        CompletableFuture.runAsync(() -> {
        }, CompletableFuture.delayedExecutor(100, TimeUnit.MILLISECONDS)).join();

        byte[] requestBuffer = new byte[]{
                (byte) 0x49, (byte) 0x96, (byte) 0x02, (byte) 0xd2, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xb6, (byte) 0x69, (byte) 0xfd, (byte) 0x2e};

        ConverterUtils.put(requestBuffer, 12, modem.getId());
        tcpClient.send(requestBuffer);
    }
}
