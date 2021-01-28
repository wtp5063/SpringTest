package com.example.demo.common.validation.order;

import javax.validation.GroupSequence;

/**
 * バリデーションの順番を定義するinterface。
 * @author tanakamasato
 * @since 2021/01/27
 */
@GroupSequence({ValidGroup1.class, ValidGroup2.class})
public interface GroupOrder
{

}
