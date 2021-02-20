package com.example.mylibrary.gof.chain;

public class ShanghaiPostman extends Postman {
    @Override
    public String process(String address) {
        System.out.println("IN Chain ShanghaiPostman address = " + address);
        if (address.equals("Shanghai")) {//北京地区的则派送
            System.out.println("派送到上海");
            return address + " ShanghaiPostman has processed.";
        } else {//否则交给下一个快递员去处理
            System.out.println("IN Chain ShanghaiPostman nextPostman process.");
            String result = nextPostman.process(address);
            System.out.println("IN Chain ShanghaiPostman nextPostman process res = "+ result);
            return result;
        }
    }
}
