package com.astrabank.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accountId;
	
	private String accountNumber;
    private AccountType accountType;
    private AccountOrCardStatus status=AccountOrCardStatus.Active;
    private long balance;
    
    
    private String username;
    
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;
    
    // Mappings 
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnoreProperties("accounts")
    private Customer customer;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transaction = new ArrayList<>();
    
    
}
