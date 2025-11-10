package com.petlearning.restapi.service;

import com.petlearning.restapi.entity.Owner;
import com.petlearning.restapi.exception.ResourceNotFoundException;
import com.petlearning.restapi.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 飼主業務邏輯服務層
 * 封裝飼主相關的業務邏輯
 */
@Service
public class OwnerService {
    
    private final OwnerRepository ownerRepository;
    
    /**
     * 建構子注入 Repository
     */
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
    
    /**
     * 查詢所有飼主
     */
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }
    
    /**
     * 依 ID 查詢飼主
     */
    public Owner findById(Long id) {
        return ownerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("找不到 ID 為 " + id + " 的飼主"));
    }
    
    /**
     * 建立新飼主
     */
    public Owner create(Owner owner) {
        return ownerRepository.save(owner);
    }
    
    /**
     * 更新飼主資訊
     */
    public Owner update(Long id, Owner ownerDetails) {
        Owner owner = findById(id);
        owner.setFirstName(ownerDetails.getFirstName());
        owner.setLastName(ownerDetails.getLastName());
        owner.setCity(ownerDetails.getCity());
        owner.setTelephone(ownerDetails.getTelephone());
        return ownerRepository.save(owner);
    }
    
    /**
     * 刪除飼主
     */
    public void delete(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new ResourceNotFoundException("找不到 ID 為 " + id + " 的飼主");
        }
        ownerRepository.deleteById(id);
    }
}
