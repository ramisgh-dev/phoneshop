package com.sip.ams.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

@Entity

public class Phone {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Label is mandatory")
    @Column(name = "label")
    private String label;
    
    @Column(name = "picture")
    private String picture;

    @Min(value=100)
    @Column(name = "price")
    private float price;

    public Phone() {}

    public Phone(String label, float price,String picture) {
        this.price = price;
        this.label = label;
        this.picture=picture;
        }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
    public void setPicture(String picture) {
    	this.picture = picture;
    	}
    	public String getPicture() {
    	return picture;
    	}


	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	
	/**** Many To One ****/
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;
	
	
	public Category getProvider() {
    	return category;
    }
    
    public void setProvider(Category category) {
    	this.category=category;
    }  
    
}
