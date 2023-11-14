package com.example.demo.repository;

import com.example.demo.entity.Custom;
import com.example.demo.entity.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Custom 엔티티에 대한 검색을 위한 Specification을 정의합니다.
 */
@RequiredArgsConstructor
@Service
public class CustomSpecification {
    private final CustomRepository customRepository;

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
                spec = Specification.where(CustomSpecification.equalBusiNum(busiNum));
            } else {
                spec = spec.or(CustomSpecification.equalBusiNum(busiNum));
            }
        }

        // 거래처명에 따른 검색 조건
        if(custom.getData().getCustom() != null){
            // 거래처명에 대한 Specification 추가
            if(spec == null){
                spec = Specification.where(CustomSpecification.equalCustom(custom.getData().getCustom().trim()));
            } else {
                spec = spec.or(CustomSpecification.equalCustom(custom.getData().getCustom().trim()));
            }
        }

        // Specification을 이용하여 Custom 엔티티를 검색합니다.
        return customRepository.findAll(spec);
    }
}
