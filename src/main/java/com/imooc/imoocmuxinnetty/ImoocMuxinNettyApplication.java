package com.imooc.imoocmuxinnetty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.imooc"})
@MapperScan(basePackages = {"com.imooc.mapper"})
public class ImoocMuxinNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImoocMuxinNettyApplication.class, args);
    }

}
