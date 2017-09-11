package com.dingtalk.isv.access.api.model.crm;

import java.io.Serializable;

/**
 * @author qingshui.yf 10/13/15
 */
public class CrmExtPropertyVO implements Serializable {

    private static final long serialVersionUID = 1151556183351299178L;

    /**
     * 鎵╁睍淇℃伅锛屽睍绀虹殑鍚嶇О濡傦細閮ㄩ棬锛屽叕鍙革紝鑱屼綅绛�
     */
    private String            itemName;

    /**
     * 鎵╁睍淇℃伅锛屽睍绀虹殑鍚嶇О鐨勫��
     */
    private String            itemValue;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
}
