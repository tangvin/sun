package com.example.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DebitCardLifeCycleBO {

    //借记卡卡号
    private String dbcrdCrdno;
    //卡片生命周期
    private String cycleState;
}
