package com.myproject.order_processing.dto;

/**
 * CustomerDTO - Customer data ko ek layer se doosri layer tak bhejne ke liye use hota hai.
 * Yeh class client aur server ke beech data transfer ke liye banayi gayi hai.
 */
public class CustomerDTO {
    /** Customer ki unique ID (हर कस्टमर की अलग पहचान) */
    private Long id;
    /** Customer ka naam (Name of the customer) */
    private String name;
    /** Customer ka email (Email address) */
    private String email;
    /** Customer ka address (Address) */
    private String address;
    /** Customer ka role (USER/ADMIN etc.) */
    private String role;
    /** Customer ka contact number (Mobile ya phone number) */
    private String contactNumber;
    /** Customer ka password (Account ke liye password) */
    private String password;

    // Getter aur Setter methods - Data ko safely access aur modify karne ke liye
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
