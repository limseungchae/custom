package com.example.custom.controller;

import com.example.custom.entity.Header;
import com.example.custom.dto.CustomApi;
import com.example.custom.service.CustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomRestController {
    private final CustomService customService;

    // 거래처 리스트
    @GetMapping("/api/list_custom")
    public List<CustomApi> list(){
        return customService.list();
    }

    // 조건검색
    @PostMapping("/api/search_list")
    public List<CustomApi> searchList(@RequestBody Header<CustomApi> request) {
        return customService.searchList(request);
    }

    // 상세(사업자번호)
    @GetMapping("/api/read_custom/{busiNum}")
    public Header<CustomApi> read(@PathVariable String busiNum){
        return customService.read(busiNum);
    }

    //등록
    @PostMapping("/api/regist_custom")
    public Header<CustomApi> create(@RequestBody Header<CustomApi> request) {
        return customService.create(request);
    }

    // 삭제
    @DeleteMapping("/api/delete_custom/{busiNum}")
    public Header<CustomApi> delete(@PathVariable String busiNum){
        return customService.delete(busiNum);
    }

    // 정보 수정
    @PutMapping("/api/update_custom")
    public Header<CustomApi> update(@RequestBody Header<CustomApi> request) {
        return customService.update(request);
    }
}
