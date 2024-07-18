package kr.contec.fromto.cam.safran.dto;

import kr.contec.fromto.framework.domain.CamConfig;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Modem {
    CamConfig.Meta meta;
    CamConfig.Access access;
    String fullWriteFileName;
    String caduWriteFileName;
    int id;
}
