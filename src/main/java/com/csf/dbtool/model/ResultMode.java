package com.csf.dbtool.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResultMode {

    private Integer code;

    private String msg;

    private Object result;
}
