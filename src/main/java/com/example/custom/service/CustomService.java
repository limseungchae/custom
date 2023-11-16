package com.example.custom.service;

import com.example.custom.entity.Account;
import com.example.custom.entity.Custom;
import com.example.custom.entity.Header;
import com.example.custom.repository.AccountRepository;
import com.example.custom.dto.CustomApi;
import com.example.custom.repository.CustomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public Header<CustomApi> create(Header<CustomApi> request) {
        CustomApi customApiRequest = request.getData();

        Custom custom = Custom.builder()
                .busiNum(customApiRequest.getBusiNum())
                .custom(customApiRequest.getCustom())
                .shortName(customApiRequest.getShortName())
                .ceo(customApiRequest.getCeo())
                .chargePerson(customApiRequest.getChargePerson())
                .busiCondition(customApiRequest.getBusiCondition())
                .item(customApiRequest.getItem())
                .postNum(customApiRequest.getPostNum())
                .addr1(customApiRequest.getAddr1())
                .addr2(customApiRequest.getAddr2())
                .tel(customApiRequest.getTel())
                .fax(customApiRequest.getFax())
                .homepage(customApiRequest.getHomepage())
                .coYn(customApiRequest.getCoYn())
                .foreignYn(customApiRequest.getForeignYn())
                .taxYn(customApiRequest.getTaxYn())
                .countryEng(customApiRequest.getCountryEng())
                .countryKor(customApiRequest.getCountryKor())
                .specialRelation(customApiRequest.getSpecialRelation())
                .tradeStop(customApiRequest.getTradeStop())
                .contractPeriodS(customApiRequest.getContractPeriodS())
                .contractPeriodE(customApiRequest.getContractPeriodE())
                .regiInfoMan(customApiRequest.getRegiInfoMan())
                .regiInfoDate(customApiRequest.getRegiInfoDate())
                .build();
        Custom custom1 = customRepository.save(custom);

        Account account = Account.builder()
                .busiNum(customApiRequest.getBusiNum())
                .factory(customApiRequest.getFactory())
                .tradeBank(customApiRequest.getTradeBank())
                .accountNum(customApiRequest.getAccountNum())
                .build();
        Account account1 = accountRepository.save(account);

        return Header.OK(response(custom1, account1));
    }


    // 상세정보 조회
    @Transactional
    public Header<CustomApi> read(String busiNum) {
        // 사업자 번호 길이가 20이 아닌 경우 처리
        if (busiNum.length() != 20) {
            // 처리 로직 작성
        }
        // 주어진 사업자 번호에 해당하는 Custom 엔티티를 조회합니다
        Custom custom = customRepository.findByBusiNum(busiNum);
        Account account = accountRepository.findByBusiNum(busiNum);


        // 조회된 Custom 엔티티를 반환합니다
        CustomApi customApiResponse = mapToCustomApi(custom, account);
        return Header.OK(customApiResponse);
    }

    private CustomApi mapToCustomApi(Custom custom, Account account) {
        return CustomApi.builder()
                .busiNum(custom.getBusiNum())
                .custom(custom.getCustom())
                .shortName(custom.getShortName())
                .ceo(custom.getCeo())
                .chargePerson(custom.getChargePerson())
                .busiCondition(custom.getBusiCondition())
                .item(custom.getItem())
                .postNum(custom.getPostNum())
                .addr1(custom.getAddr1())
                .addr2(custom.getAddr2())
                .tel(custom.getTel())
                .fax(custom.getFax())
                .homepage(custom.getHomepage())
                .coYn(custom.getCoYn())
                .foreignYn(custom.getForeignYn())
                .taxYn(custom.getTaxYn())
                .countryEng(custom.getCountryEng())
                .countryKor(custom.getCountryKor())
                .specialRelation(custom.getSpecialRelation())
                .tradeStop(custom.getTradeStop())
                .contractPeriodS(custom.getContractPeriodS())
                .contractPeriodE(custom.getContractPeriodE())
                .regiInfoMan(custom.getRegiInfoMan())
                .regiInfoDate(custom.getRegiInfoDate())
                .modiInfoMan(custom.getModiInfoMan())
                .modiInfoDate(custom.getModiInfoDate())
                .factory(account.getFactory())
                .tradeBank(account.getTradeBank())
                .accountNum(account.getAccountNum())
                .build();
    }
    // 수정 기능
    @Transactional
    public Header<CustomApi> update(Header<CustomApi> request) {
        CustomApi customApiRequest = request.getData();

        String busiNum = customApiRequest.getBusiNum().trim();

        Custom custom = Custom.builder()
                .busiNum(busiNum)
                .custom(customApiRequest.getCustom())
                .shortName(customApiRequest.getShortName())
                .ceo(customApiRequest.getCeo())
                .chargePerson(customApiRequest.getChargePerson())
                .busiCondition(customApiRequest.getBusiCondition())
                .item(customApiRequest.getItem())
                .postNum(customApiRequest.getPostNum())
                .addr1(customApiRequest.getAddr1())
                .addr2(customApiRequest.getAddr2())
                .tel(customApiRequest.getTel())
                .fax(customApiRequest.getFax())
                .homepage(customApiRequest.getHomepage())
                .coYn(customApiRequest.getCoYn())
                .foreignYn(customApiRequest.getForeignYn())
                .taxYn(customApiRequest.getTaxYn())
                .countryEng(customApiRequest.getCountryEng())
                .countryKor(customApiRequest.getCountryKor())
                .specialRelation(customApiRequest.getSpecialRelation())
                .tradeStop(customApiRequest.getTradeStop())
                .contractPeriodS(customApiRequest.getContractPeriodS())
                .contractPeriodE(customApiRequest.getContractPeriodE())
                .regiInfoMan(customApiRequest.getRegiInfoMan())
                .regiInfoDate(customApiRequest.getRegiInfoDate())
                .contractPeriodE(customApiRequest.getContractPeriodE())
                .modiInfoMan(customApiRequest.getModiInfoMan())
                .modiInfoDate(customApiRequest.getModiInfoDate())
                .build();
        Custom custom1 = customRepository.save(custom);

        Account account = Account.builder()
                .busiNum(busiNum)
                .factory(customApiRequest.getFactory())
                .tradeBank(customApiRequest.getTradeBank())
                .accountNum(customApiRequest.getAccountNum())
                .build();
        Account account1 = accountRepository.save(account);

        return Header.OK(response(custom1, account1));
    }

    public CustomApi response(Custom custom, Account account) {
        return CustomApi.builder()
                .busiNum(trimIfNotNull(custom.getBusiNum()))
                .custom(trimIfNotNull(custom.getCustom()))
                .shortName(trimIfNotNull(custom.getShortName()))
                .ceo(trimIfNotNull(custom.getCeo()))
                .chargePerson(trimIfNotNull(custom.getChargePerson()))
                .busiCondition(trimIfNotNull(custom.getBusiCondition()))
                .item(trimIfNotNull(custom.getItem()))
                .postNum(trimIfNotNull(custom.getPostNum()))
                .addr1(trimIfNotNull(custom.getAddr1()))
                .addr2(trimIfNotNull(custom.getAddr2()))
                .tel(trimIfNotNull(custom.getTel()))
                .fax(trimIfNotNull(custom.getFax()))
                .homepage(trimIfNotNull(custom.getHomepage()))
                .coYn(custom.getCoYn())
                .foreignYn(custom.getForeignYn())
                .taxYn(custom.getTaxYn())
                .countryEng(trimIfNotNull(custom.getCountryEng()))
                .countryKor(trimIfNotNull(custom.getCountryKor()))
                .specialRelation(custom.getSpecialRelation())
                .tradeStop(custom.getTradeStop())
                .contractPeriodS(custom.getContractPeriodS())
                .contractPeriodE(custom.getContractPeriodE())
                .regiInfoMan(trimIfNotNull(custom.getRegiInfoMan()))
                .regiInfoDate(custom.getRegiInfoDate())
                .contractPeriodE(custom.getContractPeriodE())
                .modiInfoMan(trimIfNotNull(custom.getModiInfoMan()))
                .modiInfoDate(custom.getModiInfoDate())
                .factory(trimIfNotNull(account.getFactory()))
                .tradeBank(trimIfNotNull(account.getTradeBank()))
                .accountNum(trimIfNotNull(account.getAccountNum()))
                .build();
    }


    // 주어진 문자열이 null이 아닌 경우 앞뒤 공백을 제거한 문자열을 반환합니다.
    private String trimIfNotNull(String value) {
        return value == null ? null : value.trim();
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
    public List<CustomApi> searchList(Header<CustomApi> request) {
        // Custom 검색 조건에 따라 엔티티를 조회합니다
        List<Custom> customerList = searchCustomList(request);

        // 조회된 결과가 없는 경우 빈 리스트 반환
        if (customerList == null) {
            return new ArrayList<>();
        }

        // 일부 필드로 이루어진 Custom 엔티티 리스트 반환
        return customerList.stream()
                .map(custom -> CustomApi.builder()
                        .busiNum(custom.getBusiNum())
                        .custom(custom.getCustom())
                        .build())
                .collect(Collectors.toList());
    }

    // 전체 목록 조회
    public List<CustomApi> list (){
        List<Custom> customList = customRepository.findAll();
        List<CustomApi> customListApiResponseList = customList.stream()
                .map(custom -> {
                    CustomApi customApi = CustomApi.builder()
                            .busiNum(custom.getBusiNum())
                            .custom(custom.getCustom())
                            .build();
                    return customApi;
                }).collect(Collectors.toList());
        return customListApiResponseList;
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
    public List<Custom> searchCustomList(Header<CustomApi> custom) {
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
