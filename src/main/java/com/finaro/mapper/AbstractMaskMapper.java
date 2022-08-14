package com.finaro.mapper;

import com.finaro.service.impl.MaskingService;
import com.finaro.utils.Utils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public abstract class AbstractMaskMapper {

    @Autowired
    private MaskingService maskingService;

    protected String getMaskedDecodedText(String fieldName, String fieldValue) {
        var decodeText = Utils.decode(fieldValue);
        return maskingService.maskString(fieldName, decodeText);
    }
}
