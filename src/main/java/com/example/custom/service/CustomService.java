package com.example.custom.service;

import com.example.custom.entity.Account;
import com.example.custom.entity.Custom;
import com.example.custom.entity.Header;
import com.example.custom.repository.AccountRepository;
import com.example.custom.repository.CustomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Spring의 Service로 선언하여 bean으로 관리합니다
@Service
// Lombok을 통한 생성자 주입을 사용합니다
@RequiredArgsConstructor
public class CustomService {

    // Custom 및 Account Repository를 주입받습니다
    private final CustomRepository customRepository;
    private final AccountRepository accountRepository;

    // 등록 기능
    @Transactional
    public Header<Custom> create(Header<Custom> request) {
        // 요청에서 Custom 데이터를 가져옵니다
        Custom custom = request.getData();
        // Custom 엔티티를 저장하고 저장된 엔티티를 반환합니다
        Custom savedCustom = customRepository.save(custom);
        return Header.OK(savedCustom);
    }

    // 상세정보 조회
    @Transactional
    public Header<Custom> read(String busiNum) {
        // 사업자 번호 길이가 20이 아닌 경우 처리
        if (busiNum.length() != 20) {
            // 처리 로직 작성
        }
        // 주어진 사업자 번호에 해당하는 Custom 엔티티를 조회합니다
        Custom custom = customRepository.findByBusiNum(busiNum);
        // 조회된 Custom 엔티티를 반환합니다
        return Header.OK(custom);
    }

    // 수정 기능
    @Transactional
    public Header<Custom> update(Header<Custom> request) {
        // 요청에서 Custom 데이터를 가져옵니다
        Custom custom = request.getData();
        // Custom 엔티티를 수정하고 저장된 엔티티를 반환합니다
        Custom savedCustom = customRepository.save(custom);
        return Header.OK(savedCustom);
    }

    // 삭제 기능
    public Header delete(String busiNum) {
        // 사업자 번호 길이가 20이 아닌 경우 처리
        if (busiNum.length() != 20) {
            // 처리 로직 작성
        }

        // 주어진 사업자 번호에 해당하는 Custom 및 Account 엔티티를 조회합니다
        Optional<Custom> optionalCustom = customRepository.getByBusiNum(busiNum);
        Optional<Account> optionalAccount = accountRepository.getByBusiNum(busiNum);

        // Custom 엔티티가 존재하는 경우 삭제를 수행합니다
        return optionalCustom.map(custom -> {
            customRepository.delete(custom);
            // Account 엔티티가 존재하는 경우 Account 엔티티도 삭제를 수행합니다
            return optionalAccount.map(account -> {
                accountRepository.delete(account);
                // 삭제 완료를 응답합니다
                return Header.OK();
            }).orElseGet(() -> Header.ERROR("Account 정보를 찾을 수 없음"));
        }).orElseGet(() -> Header.ERROR("Custom 정보를 찾을 수 없음"));
    }

    // 검색조회
    public List<Custom> searchList(Header<Custom> request) {
        // Custom 검색 조건에 따라 엔티티를 조회합니다
        List<Custom> customerList = searchCustomList(request);

        // 조회된 결과가 없는 경우 빈 리스트 반환
        if (customerList == null) {
            return new ArrayList<>();
        }

        // 일부 필드로 이루어진 Custom 엔티티 리스트 반환
        return customerList.stream()
                .map(custom -> Custom.builder()
                        .busiNum(custom.getBusiNum())
                        .custom(custom.getCustom())
                        .build())
                .collect(Collectors.toList());
    }

    // 전체 목록 조회
    public List<Custom> list() {
        // 모든 Custom 엔티티를 조회하여 반환합니다
        return customRepository.findAll();
    }

    // 사양 조건에 따른 Custom 검색을 수행하는 메서드
    public static Specification<Custom> equalBusiNum(String busiNum) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("busiNum"), busiNum);
    }

    // 사양 조건에 따른 Custom 검색을 수행하는 메서드
    public static Specification<Custom> equalCustom(String custom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("custom"), custom);
    }

    // Custom 검색을 수행하는 메서드
    public List<Custom> searchCustomList(Header<Custom> custom) {
        Specification<Custom> spec = null;

        // 사업자 번호에 따른 검색 조건
        if (custom.getData().getBusiNum() != null) {
            String busiNum = custom.getData().getBusiNum().trim();

            // 사업자 번호에 대한 Specification 추가
            if (spec == null) {
                spec = Specification.where(CustomService.equalBusiNum(busiNum));
            } else {
                spec = spec.or(CustomService.equalBusiNum(busiNum));
            }
        }

        // 거래처명에 따른 검색 조건
        if (custom.getData().getCustom() != null) {
            // 거래처명에 대한 Specification 추가
            if (spec == null) {
                spec = Specification.where(CustomService.equalCustom(custom.getData().getCustom().trim()));
            } else {
                spec = spec.or(CustomService.equalCustom(custom.getData().getCustom().trim()));
            }
        }

        // Specification을 이용하여 Custom 엔티티를 검색합니다
        return customRepository.findAll(spec);
    }
}
