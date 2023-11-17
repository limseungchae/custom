package com.example.custom.repository;


import com.example.custom.entity.Custom;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CustomRepository extends JpaRepository<Custom, String>, JpaSpecificationExecutor<Custom> {

    // 복수의 Custom 엔터티를 반환하는 Specification에 따라 모든 Custom 엔터티 목록을 가져옵니다.
    List<Custom> findAll(Specification<Custom> spec);

    // 주어진 사업자번호에 해당하는 Custom 엔터티를 반환합니다.
    Custom findByBusiNum(String busiNum);

    // 주어진 사업자번호에 해당하는 Optional Custom 엔터티를 반환합니다.
    Optional<Custom> getByBusiNum(String busiNum);
}
