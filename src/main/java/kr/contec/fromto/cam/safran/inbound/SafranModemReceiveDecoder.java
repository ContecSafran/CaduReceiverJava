package kr.contec.fromto.cam.safran.inbound;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import kr.contec.fromto.cam.safran.modem.ModemReceiver;
import lombok.AllArgsConstructor;

/**
 * 사프란 TM 디코더
 *
 * @author skjung
 */
@ChannelHandler.Sharable
@AllArgsConstructor
public class SafranModemReceiveDecoder extends ChannelInboundHandlerAdapter {
    private final ModemReceiver modemReceiver;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] buffer = (byte[]) msg;
        modemReceiver.write(buffer);
       // receiveCallable.receive(buffer,chId);
    }
}
