package com.beaconfire.housingservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeResponse {
    private String id; // employee id
    private String firstName;
    private String lastName;
    private String middleName;
    private String preferredName;
    private String email;
    private String cellPhone;
    private String alternatePhone;
    private String gender;
    private String ssn;
    private String dob;
    private String startDate;
    private String endDate;
    private String driverLicense;
    private String driverLicenseExpiration;
    private String houseId;

    private List<Contact> contact;
    private List<Address> address;
    private List<VisaStatus> visaStatus;
    private List<PersonalDocument> personalDocument;

    @Data
    public static class Contact {
        private String id;
        private String firstName;
        private String lastName;
        private String cellPhone;
        private String alternatePhone;
        private String email;
        private String relationship;
        private String type;
    }

    @Data
    public static class Address {
        private String id;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String zipCode;
    }

    @Data
    public static class VisaStatus {
        private String id;
        private String visaType;
        private Boolean activeFlag;
        private String startDate;
        private String endDate;
        private String lastModificationDate;
    }

    @Data
    public static class PersonalDocument {
        private String id;
        private String path;
        private String title;
        private String comment;
        private String createDate;
    }
}
