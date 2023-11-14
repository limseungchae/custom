package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Custom;
import com.example.demo.entity.Header;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomRepository;
import com.example.demo.repository.CustomSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomService {

    private final CustomRepository customRepository;
    private final AccountRepository accountRepository;
    private final CustomSpecification customSpecification;

    // 등록
    @Transactional
    public Header<Custom> create(Header<Custom> request) {
        Custom custom = request.getData(); // 요청에서 데이터를 가져옴
        Custom savedCustom = customRepository.save(custom); // Custom 엔티티 저장

        return Header.OK(savedCustom); // 저장된 엔티티를 응답
    }

    // 수정
    @Transactional
    public Header<Custom> update(Header<Custom> request) {
        Custom custom = request.getData(); // 요청에서 데이터를 가져옴
        Custom savedCustom = customRepository.save(custom); // Custom 엔티티 수정 후 저장

        return Header.OK(savedCustom); // 수정된 엔티티를 응답
    }

    // 상세정보
    @Transactional
    public Header<Custom> read(String busiNum) {
        if (busiNum.length() != 20) {
            // busiNum 길이가 20이 아닌 경우에 대한 처리
        }

        Custom custom = customRepository.findByBusiNum(busiNum); // 특정 busiNum을 가진 Custom 조회

        if (custom == null) {
            // Custom 엔티티를 찾을 수 없는 경우에 대한 처리
        }

        return Header.OK(custom); // 조회된 Custom 엔티티 반환
    }

    // 검색조회
    public List<Custom> searchList(Header<Custom> request) {
        List<Custom> customerList = customSpecification.searchCustomList(request); // Custom 검색 조건에 따라 조회

        if (customerList == null) {
            return new ArrayList<>(); // null일 경우 빈 리스트 반환
        }

        return customerList.stream()
                .map(custom -> Custom.builder()
                        .busiNum(custom.getBusiNum())
                        .custom(custom.getCustom())
                        .build())
                .collect(Collectors.toList()); // Custom 엔티티의 일부 필드로 구성된 리스트 반환
    }

    // 삭제
    public Header delete(String busiNum) {
        if (busiNum.length() != 20) {
            // busiNum 길이가 20이 아닌 경우에 대한 처리
        }

        Optional<Custom> optionalCustom = customRepository.getByBusiNum(busiNum); // 특정 busiNum을 가진 Custom 조회
        Optional<Account> optionalAccount = accountRepository.getByBusiNum(busiNum); // 특정 busiNum을 가진 Account 조회

        return optionalCustom.map(custom -> {
            customRepository.delete(custom); // Custom 엔티티 삭제
            return optionalAccount.map(account -> {
                accountRepository.delete(account); // Account 엔티티 삭제
                return Header.OK(); // 삭제 완료 응답
            }).orElseGet(() -> Header.ERROR("Account information not found")); // Account가 없을 경우 응답
        }).orElseGet(() -> Header.ERROR("Custom information not found")); // Custom이 없을 경우 응답
    }

    // 조회
    public List<Custom> list() {
        return customRepository.findAll(); // 모든 Custom 엔티티 목록 조회
    }
}
