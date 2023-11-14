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

@Service
@RequiredArgsConstructor
public class CustomService {

    private final CustomRepository customRepository;
    private final AccountRepository accountRepository;

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
        List<Custom> customerList = searchCustomList(request); // Custom 검색 조건에 따라 조회

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



    // 검색기능
    /**
     * 사업자 번호에 대한 Specification을 생성합니다.
     *
     * @param busiNum 검색할 사업자 번호
     * @return 사업자 번호를 기준으로 하는 Specification
     */
    public static Specification<Custom> equalBusiNum(String busiNum) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("busiNum"), busiNum);
    }

    /**
     * 거래처명에 대한 Specification을 생성합니다.
     *
     * @param custom 검색할 거래처명
     * @return 거래처명을 기준으로 하는 Specification
     */
    public static Specification<Custom> equalCustom(String custom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("custom"), custom);
    }

    /**
     * Header 내 Custom 데이터를 활용하여 조건 검색을 수행합니다.
     *
     * @param custom Header 내의 Custom 데이터
     * @return 조건에 부합하는 Custom 엔티티 리스트
     */
    public List<Custom> searchCustomList(Header<Custom> custom){
        Specification<Custom> spec = null;

        // 사업자 번호에 따른 검색 조건
        if(custom.getData().getBusiNum() != null){
            String busiNum = custom.getData().getBusiNum().trim();

            // 사업자 번호에 대한 Specification 추가
            if(spec == null){
                spec = Specification.where(CustomService.equalBusiNum(busiNum));
            } else {
                spec = spec.or(CustomService.equalBusiNum(busiNum));
            }
        }

        // 거래처명에 따른 검색 조건
        if(custom.getData().getCustom() != null){
            // 거래처명에 대한 Specification 추가
            if(spec == null){
                spec = Specification.where(CustomService.equalCustom(custom.getData().getCustom().trim()));
            } else {
                spec = spec.or(CustomService.equalCustom(custom.getData().getCustom().trim()));
            }
        }

        // Specification을 이용하여 Custom 엔티티를 검색합니다.
        return customRepository.findAll(spec);
    }
}



