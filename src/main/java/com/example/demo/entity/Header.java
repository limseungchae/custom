package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

    // API 통신 시간
    private LocalDateTime transactionTime;

    // API 응답 코드
    private String resultCode;

    // API 설명
    private String description;

    // 데이터
    private T data;

    // 항목 수
    private Long count;

    // 문자열 목록
    private List<String> stringList;

    // 정상 응답 생성 메서드
    public static <T> Header<T> OK() {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    // 데이터 포함한 정상 응답 생성 메서드
    public static <T> Header<T> OK(T data) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .build();
    }

    // 에러 응답 생성 메서드
    public static <T> Header<T> ERROR(String description) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();
    }

    // 데이터와 문자열 목록을 포함한 정상 응답 생성 메서드
    public static <T> Header<T> OK(T data, List<String> stringList) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .stringList(stringList)
                .build();
    }
}
