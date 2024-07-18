package kr.contec.fromto.cam.safran;

import kr.contec.fromto.framework.Cam;
import kr.contec.fromto.framework.CamConnection;
import kr.contec.fromto.framework.CamVersion;
import kr.contec.fromto.framework.domain.CamControl;
import kr.contec.fromto.framework.domain.CamStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * GeoSync Ku-Up Converter Version 정보
 *
 * @author grkim
 */
@Getter
@AllArgsConstructor
public enum HdrCaduReceiverVersion implements CamVersion {
    SATCORE(HdrCaduReceiverCam.class, HdrCaduReceiverConnection.class);

    private final Class<? extends Cam<? extends CamControl, ? extends CamStatus>> camClass;
    private final Class<? extends CamConnection> connectionClass;
}
