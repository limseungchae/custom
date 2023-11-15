package com.example.custom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Account {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)   // 자동증가
    @Column(name = "BUSI_NUM")
    private String busiNum;

    @Column(name = "FACTORY")
    private String factory;

    @Column(name = "TRADE_BANK")
    private String tradeBank;

    @Column(name = "ACCOUNT_NUM")
    private String accountNum;
}

