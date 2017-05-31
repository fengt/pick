package com.itiaoling.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class User {

    private Long id;

    @NotNull(message = "缺少name字段")
    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotNull(message = "缺少email字段")
    @NotBlank(message = "email 不能为空")
    @Email(message = "email 格式不正确")
    private String email;
    
    @NotNull(message = "缺少password字段")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 10, message = "密码长度必须在6～10之间")
    private String password;

    
    
    public User() {
    	
	}

	public User(Long id, String name, String email, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
