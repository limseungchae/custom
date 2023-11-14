package com.example.custom.repository;

import com.example.custom.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Account 엔티티에 대한 Repository 인터페이스입니다.
 * JpaRepository 및 JpaSpecificationExecutor를 확장하여 Account 엔티티의 CRUD 작업을 관리합니다.
 */
public interface AccountRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {

    /**
     * 사업자 번호를 기준으로 Account 엔티티를 조회합니다.
     *
     * @param busiNum 조회할 사업자 번호
     * @return 사업자 번호와 연관된 Account 엔티티를 포함한 Optional
     */
    Optional<Account> getByBusiNum(String busiNum);
}
