package com.example.mylibrary.gof.chain;

public class GuangzhouPostman extends Postman {
    @Override
    public String process(String address) {
        System.out.println("IN Chain GuangzhouPostman address = " + address);

        if (address.equals("Guangzhou")) {//北京地区的则派送
            System.out.println("派送到广州");
            return address + " GuangzhouPostman has processed.";
        } else {//否则交给下一个快递员去处理
            if (nextPostman != null) {
                System.out.println("IN Chain GuangzhouPostman nextPostman process.");
                String result = nextPostman.process(address);
                System.out.println("IN Chain GuangzhouPostman nextPostman process res = " + result);
                return result;
            }
            System.out.println("IN Chain GuangzhouPostman nextPostman process res = null");
            return null;
        }
    }
}
