package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicUpdate
@DynamicInsert
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

