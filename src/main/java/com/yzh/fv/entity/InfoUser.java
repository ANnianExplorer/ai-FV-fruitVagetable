package com.yzh.fv.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 通知用户关联表
 *
 * @author 杨振华
 * @since 2023/5/31
 */
@Data
public class InfoUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    //通知Id
    private Long infoId;

    //用户id
    private Long userId;
}
