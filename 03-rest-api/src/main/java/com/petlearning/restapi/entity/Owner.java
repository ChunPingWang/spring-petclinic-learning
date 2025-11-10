package com.petlearning.restapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * 飼主實體
 * 對應資料庫中的 owners 表
 */
@Entity
@Table(name = "owners")
public class Owner {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "名字不可為空")
    @Size(min = 1, max = 50, message = "名字長度必須在 1-50 之間")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @NotBlank(message = "姓氏不可為空")
    @Size(min = 1, max = 50, message = "姓氏長度必須在 1-50 之間")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    
    @Size(max = 100, message = "城市名稱不可超過 100 字元")
    @Column(length = 100)
    private String city;
    
    @Pattern(regexp = "^(09\\d{8})?$", message = "電話格式錯誤（應為 09 開頭的 10 碼）")
    @Column(length = 20)
    private String telephone;
    
    /**
     * 建構子：建立飼主時必須提供姓名
     */
    public Owner(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("姓名不可為空");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Protected 無參建構子（JPA 需要）
     */
    protected Owner() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
