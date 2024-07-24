package com.xkcoding.exception.handler.constant;

public enum ResTypes {

       System("biz","分组");
       String  code;
       String desc;

       ResTypes(String code,String desc){
              this.code=code;
              this.desc=desc;
       };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public  static  ResTypes  getResTypeByCode(String code){
        for (ResTypes value : ResTypes.values()) {
              if (value.getCode().equals(code)){
                   return  value;
              }
        }
         throw new IllegalArgumentException("未知的编码"+code);
    }
}
