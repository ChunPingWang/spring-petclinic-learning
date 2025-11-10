package com.petlearning.restapi.controller;

import com.petlearning.restapi.dto.ApiResponse;
import com.petlearning.restapi.entity.Owner;
import com.petlearning.restapi.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 飼主管理 API Controller
 * 基本路徑：/api/owners
 * 
 * 提供以下 REST API：
 * - GET /api/owners - 查詢所有飼主
 * - GET /api/owners/{id} - 查詢單一飼主
 * - POST /api/owners - 新增飼主
 * - PUT /api/owners/{id} - 更新飼主
 * - DELETE /api/owners/{id} - 刪除飼主
 */
@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    
    private final OwnerService ownerService;
    
    /**
     * 建構子注入 Service
     */
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }
    
    /**
     * 查詢所有飼主
     * GET /api/owners
     * 
     * @return 所有飼主的清單
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Owner>>> getAllOwners() {
        List<Owner> owners = ownerService.findAll();
        return ResponseEntity.ok(ApiResponse.success(owners));
    }
    
    /**
     * 根據 ID 查詢單一飼主
     * GET /api/owners/{id}
     * 
     * @param id 飼主 ID
     * @return 飼主資訊
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Owner>> getOwnerById(@PathVariable Long id) {
        Owner owner = ownerService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(owner));
    }
    
    /**
     * 新增飼主
     * POST /api/owners
     * 
     * @param owner 新飼主資訊
     * @return 新增後的飼主資訊（包含 ID）
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Owner>> createOwner(@Valid @RequestBody Owner owner) {
        Owner savedOwner = ownerService.create(owner);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("飼主已成功新增", savedOwner));
    }
    
    /**
     * 更新飼主資訊
     * PUT /api/owners/{id}
     * 
     * @param id 飼主 ID
     * @param ownerDetails 更新的飼主資訊
     * @return 更新後的飼主資訊
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Owner>> updateOwner(
            @PathVariable Long id,
            @Valid @RequestBody Owner ownerDetails) {
        Owner updatedOwner = ownerService.update(id, ownerDetails);
        return ResponseEntity.ok(ApiResponse.success("飼主資訊已更新", updatedOwner));
    }
    
    /**
     * 刪除飼主
     * DELETE /api/owners/{id}
     * 
     * @param id 飼主 ID
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
