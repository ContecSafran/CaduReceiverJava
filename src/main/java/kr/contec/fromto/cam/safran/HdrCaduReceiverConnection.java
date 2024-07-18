package kr.contec.fromto.cam.safran;

import kr.contec.fromto.framework.CamConnection;
import kr.contec.fromto.framework.constants.CamProtocol;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * GeoSync Ku-Up Converter Connection
 *
 * @author grkim
 */
@Getter
@AllArgsConstructor
public enum HdrCaduReceiverConnection implements CamConnection {
    MONITORING_CONTROL(CamProtocol.TCP, 3075, true);

    private final CamProtocol protocol;
    private final int defaultPort;
    private final boolean persistence;
}
