package com.example.custom.controller;

import com.example.custom.entity.Custom;
import com.example.custom.entity.Header;
import com.example.custom.service.CustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CustomRestController {
    private final CustomService customService;

    // 거래처 리스트
    @GetMapping("/api/list_custom")
    public List<Custom> list(){
        return customService.list();
    }

    // 조건검색
    @PostMapping("/api/search_list")
    public List<Custom> searchList(@RequestBody Header<Custom> request) {
        return customService.searchList(request);
    }

    // 상세(사업자번호)
    @GetMapping("/api/read_custom/{busiNum}")
    public Header<Custom> read(@PathVariable String busiNum){
        return customService.read(busiNum);
    }

    //등록
    @PostMapping("/api/regist_custom")
    public Header<Map<String, Object>> create(@RequestBody Header<Custom> request) {
        return customService.create(request);
    }

    // 삭제
    @DeleteMapping("/api/delete_custom/{busiNum}")
    public Header<Custom> delete(@PathVariable String busiNum){
        return customService.delete(busiNum);
    }

    // 정보 수정
    @PutMapping("/api/update_custom")
    public Header<Custom> update(@RequestBody Header<Custom> request) {
        return customService.update(request);
    }
}
