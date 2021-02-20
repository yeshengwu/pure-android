package com.example.mylibrary.gof.chain;

public class BeijingPostman extends Postman {
    @Override
    public String process(String address) {
        System.out.println("IN Chain BeijingPostman address = " + address);

        if (address.equals("Beijing")) {//北京地区的则派送
            System.out.println("派送到北京");
            return address + " BeijingPostman has processed.";
        } else {//否则交给下一个快递员去处理
            System.out.println("IN Chain BeijingPostman nextPostman process.");
            String result = nextPostman.process(address);
            System.out.println("IN Chain BeijingPostman nextPostman process res = " + result);
            return result;
        }
    }
}