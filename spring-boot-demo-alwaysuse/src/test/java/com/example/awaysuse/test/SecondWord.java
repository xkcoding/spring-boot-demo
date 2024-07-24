package com.example.awaysuse.test;
import com.hankcs.hanlp.HanLP;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class SecondWord {

    @Test
    public  void  segment() {
        String text="电网公平开放监管办法";
        List<String> collect = HanLP.segment(text)
            .stream()
            .map(term -> term.word)
            .collect(Collectors.toList());

        collect.stream().forEach(System.out::println);
    }


 }
