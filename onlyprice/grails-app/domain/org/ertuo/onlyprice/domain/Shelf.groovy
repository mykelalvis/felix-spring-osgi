/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */
package org.ertuo.onlyprice.domain

import java.util.Date;

/**
 * 
 * 货架
 * @author mo.duanm
 * @version $Id: Shelf.groovy, v 0.1 2011-4-14 下午06:40:36 mo.duanm Exp $
 */
class Shelf {

    /**商品  */
    Goods goods

    /**上架时间  */
    Date onTime

    /**下架时间  */
    Date offTime

    /**停滞间隔  */
    Long waitTime

    /** 出价单位 */
    String utils

    /** 创建时间 */
    Date gmtCreate

    /** 修改时间 */
    Date gmtModify=new Date()

    static mapping={
        table "Shelf"
        version false
        goods column:'goods'
    }
}