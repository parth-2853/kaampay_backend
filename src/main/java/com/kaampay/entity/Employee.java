package com.kaampay.entity;

import com.kaampay.entity.EmployeeEnums.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "employee_id", length = 36, nullable = false)
    private String employeeId;

    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;

    @Column(name = "branch_id", length = 36)
    private String branchId;

    @Column(name = "department_id", length = 36)
    private String departmentId;

    @Column(name = "designation_id", length = 36)
    private String designationId;

    @Column(name = "shift_id", length = 36)
    private String shiftId;

    @Column(name = "employee_code", length = 50, unique = true)
    private String employeeCode;

    @Column(name = "full_name", length = 255, nullable = false)
    private String fullName;

    @Column(length = 255)
    private String email;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(name = "alternate_phone", length = 20)
    private String alternatePhone;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Male','Female','Other') default 'Male'")
    private Gender gender = Gender.Male;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;

    @Column(name = "blood_group", length = 5)
    private String bloodGroup;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "profile_image_base64", columnDefinition = "LONGTEXT")
    private String profileImageBase64;

    @Enumerated(EnumType.STRING)
    @Column(name = "salary_type", columnDefinition = "enum('Monthly','Hourly','Daily') default 'Monthly'")
    private SalaryType salaryType = SalaryType.Monthly;

    @Column(name = "salary_amount", precision = 10, scale = 2)
    private BigDecimal salaryAmount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Employee','Attendance Manager','Admin','Owner') default 'Employee'")
    private UserRole role = UserRole.Employee;

    @Column(name = "punch_in_type", columnDefinition = "enum('Swipe Punch In','Selfie Punch in') default 'Swipe Punch In'")
    private String punchInType = "Swipe Punch In";

    // CHANGED: From Boolean to String
    @Column(name = "attendance_permission", length = 10)
    private String attendancePermission = "true";

    @Column(name = "attendance_location", columnDefinition = "enum('Office Location','Any Location','Office WiFi') default 'Any Location'")
    private String attendanceLocation = "Any Location";

    // CHANGED: From Boolean to String
    @Column(name = "is_active", length = 10)
    private String isActive = "true";

    @Column(name = "created_by", length = 36)
    private String createdBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // NEW COLUMNS
    @Column(name = "employment_type", length = 100)
    private String employmentType;

    @Column(name = "bank_name", length = 100)
    private String bankName;

    @Column(name = "bank_branch", length = 100)
    private String bankBranch;

    @Column(name = "bank_account_number", length = 100)
    private String bankAccountNumber;

    @Column(name = "bank_ifsc", length = 100)
    private String bankIfsc;

    @Column(name = "upi_id_detail", length = 100)
    private String upiIdDetail;

    @Column(name = "aadhar_card", columnDefinition = "LONGTEXT")
    private String aadharCard;

    @Column(name = "pan_card", columnDefinition = "LONGTEXT")
    private String panCard;

    @Column(name = "driving_license", columnDefinition = "LONGTEXT")
    private String drivingLicense;

    @Column(name = "emergency_person_name", length = 100)
    private String emergencyPersonName;

    @Column(name = "emergency_person_contact", length = 100)
    private String emergencyPersonContact;

    @Column(name = "emergency_person_relation", length = 100)
    private String emergencyPersonRelation;

    @Column(name = "emergency_person_address", length = 100)
    private String emergencyPersonAddress;

    @Column(name = "reference_name", length = 100)
    private String referenceName;

    @Column(name = "reference_mobile_no", length = 100)
    private String referenceMobileNo;

    @PrePersist
    protected void onCreate() {
        if (employeeId == null || employeeId.isEmpty()) {
            employeeId = java.util.UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (gender == null) gender = Gender.Male;
        if (salaryType == null) salaryType = SalaryType.Monthly;
        if (role == null) role = UserRole.Employee;
        if (punchInType == null || punchInType.isEmpty()) punchInType = "Swipe Punch In";
        if (attendanceLocation == null || attendanceLocation.isEmpty()) attendanceLocation = "Any Location";
        if (attendancePermission == null || attendancePermission.isEmpty()) attendancePermission = "true";
        if (isActive == null || isActive.isEmpty()) isActive = "true";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getDesignationId() { return designationId; }
    public void setDesignationId(String designationId) { this.designationId = designationId; }

    public String getShiftId() { return shiftId; }
    public void setShiftId(String shiftId) { this.shiftId = shiftId; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAlternatePhone() { return alternatePhone; }
    public void setAlternatePhone(String alternatePhone) { this.alternatePhone = alternatePhone; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public LocalDate getDateOfJoining() { return dateOfJoining; }
    public void setDateOfJoining(LocalDate dateOfJoining) { this.dateOfJoining = dateOfJoining; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getProfileImageBase64() { return profileImageBase64; }
    public void setProfileImageBase64(String profileImageBase64) { this.profileImageBase64 = profileImageBase64; }

    public SalaryType getSalaryType() { return salaryType; }
    public void setSalaryType(SalaryType salaryType) { this.salaryType = salaryType; }

    public BigDecimal getSalaryAmount() { return salaryAmount; }
    public void setSalaryAmount(BigDecimal salaryAmount) { this.salaryAmount = salaryAmount; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public String getPunchInType() { return punchInType; }
    public void setPunchInType(String punchInType) { this.punchInType = punchInType; }

    public String getAttendancePermission() { return attendancePermission; }
    public void setAttendancePermission(String attendancePermission) { this.attendancePermission = attendancePermission; }

    public String getAttendanceLocation() { return attendanceLocation; }
    public void setAttendanceLocation(String attendanceLocation) { this.attendanceLocation = attendanceLocation; }

    public String getIsActive() { return isActive; }
    public void setIsActive(String isActive) { this.isActive = isActive; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Getters and Setters for NEW COLUMNS
    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getBankBranch() { return bankBranch; }
    public void setBankBranch(String bankBranch) { this.bankBranch = bankBranch; }

    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }

    public String getBankIfsc() { return bankIfsc; }
    public void setBankIfsc(String bankIfsc) { this.bankIfsc = bankIfsc; }

    public String getUpiIdDetail() { return upiIdDetail; }
    public void setUpiIdDetail(String upiIdDetail) { this.upiIdDetail = upiIdDetail; }

    public String getAadharCard() { return aadharCard; }
    public void setAadharCard(String aadharCard) { this.aadharCard = aadharCard; }

    public String getPanCard() { return panCard; }
    public void setPanCard(String panCard) { this.panCard = panCard; }

    public String getDrivingLicense() { return drivingLicense; }
    public void setDrivingLicense(String drivingLicense) { this.drivingLicense = drivingLicense; }

    public String getEmergencyPersonName() { return emergencyPersonName; }
    public void setEmergencyPersonName(String emergencyPersonName) { this.emergencyPersonName = emergencyPersonName; }

    public String getEmergencyPersonContact() { return emergencyPersonContact; }
    public void setEmergencyPersonContact(String emergencyPersonContact) { this.emergencyPersonContact = emergencyPersonContact; }

    public String getEmergencyPersonRelation() { return emergencyPersonRelation; }
    public void setEmergencyPersonRelation(String emergencyPersonRelation) { this.emergencyPersonRelation = emergencyPersonRelation; }

    public String getEmergencyPersonAddress() { return emergencyPersonAddress; }
    public void setEmergencyPersonAddress(String emergencyPersonAddress) { this.emergencyPersonAddress = emergencyPersonAddress; }

    public String getReferenceName() { return referenceName; }
    public void setReferenceName(String referenceName) { this.referenceName = referenceName; }

    public String getReferenceMobileNo() { return referenceMobileNo; }
    public void setReferenceMobileNo(String referenceMobileNo) { this.referenceMobileNo = referenceMobileNo; }
}